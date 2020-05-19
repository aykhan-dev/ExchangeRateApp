package aykhan.task.exchange.viewModel.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import aykhan.task.exchange.local.ExchangeRate
import aykhan.task.exchange.local.getDatabase
import aykhan.task.exchange.manager.PreferencesManager
import aykhan.task.exchange.network.NetworkState
import aykhan.task.exchange.repository.ExchangeRateRepository
import aykhan.task.exchange.utils.DEFAULT_EXCHANGE_BASE
import aykhan.task.exchange.utils.DEFAULT_MULTIPLEXER
import aykhan.task.exchange.utils.DEFAULT_REQUEST_DELAY
import aykhan.task.exchange.utils.EXCHANGE_BASE_KEY
import aykhan.task.exchange.work.RefreshListWork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExchangeRateViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val sharedPreferences by lazy { PreferencesManager.getInstance(context) }

    private val exchangeRateRepository =
        ExchangeRateRepository.getInstance(getDatabase(application))

    var multiplexer = DEFAULT_MULTIPLEXER

    val exchangeRates: LiveData<List<ExchangeRate>> = exchangeRateRepository.exchangeRates

    fun fetchExchangeRates(wait: Boolean) = viewModelScope.launch {
        if (wait) delay(DEFAULT_REQUEST_DELAY)
        val state = exchangeRateRepository.getExchangeRates(
            sharedPreferences.getStringElement(EXCHANGE_BASE_KEY, DEFAULT_EXCHANGE_BASE)!!
        )
        if (state is NetworkState.Failure) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val request = OneTimeWorkRequestBuilder<RefreshListWork>()
                .setConstraints(constraints)
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }

    fun modifyData(data: List<ExchangeRate>): List<ExchangeRate> {
        (data as MutableList<ExchangeRate>).add(
            0,
            ExchangeRate(
                code = sharedPreferences.getStringElement(
                    EXCHANGE_BASE_KEY,
                    DEFAULT_EXCHANGE_BASE
                )!!,
                alphaCode = "",
                numericCode = "",
                name = "",
                rate = 0.0,
                date = "",
                inverseRate = 0.0
            )
        )
        data.map { it.rate *= multiplexer }
        return data
    }

    fun updateExchangeCode(newCode: String) {
        sharedPreferences.setStringElement(EXCHANGE_BASE_KEY, newCode)
    }

}