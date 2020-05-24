package aykhan.task.exchange.viewModel.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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
import aykhan.task.exchange.utils.*
import aykhan.task.exchange.work.RefreshListWork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExchangeRateViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val sharedPreferences by lazy { PreferencesManager.getInstance(context) }
    private val workManager by lazy { WorkManager.getInstance(context) }

    private val exchangeRateRepository =
        ExchangeRateRepository.getInstance(getDatabase(application))

    private var multiplexer = DEFAULT_MULTIPLEXER

    val exchangeRates: LiveData<List<ExchangeRate>> =
        Transformations.map(exchangeRateRepository.exchangeRates) { list ->
            val code =
                sharedPreferences.getStringElement(EXCHANGE_BASE_KEY, DEFAULT_EXCHANGE_BASE)!!
            list.filter {
                val flag = it.code != code
                if (flag) it.rate *= multiplexer
                flag
            }
        }

    fun fetchExchangeRates(wait: Boolean) = viewModelScope.launch {
        if (wait) delay(DEFAULT_REQUEST_DELAY)
        val state = exchangeRateRepository.getExchangeRates(
            sharedPreferences.getStringElement(EXCHANGE_BASE_KEY, DEFAULT_EXCHANGE_BASE)!!
        )
        if (state is NetworkState.Failure) scheduleWork()
    }

    fun modifyData(data: List<ExchangeRate>): List<ExchangeRate> {

        if (data.isEmpty()) return data

        (data as MutableList<ExchangeRate>).add(
            0,
            ExchangeRate(
                code = sharedPreferences.getStringElement(
                    key = EXCHANGE_BASE_KEY,
                    defaultValue = DEFAULT_EXCHANGE_BASE
                )!!
            )
        )

        return data
    }

    fun updateExchangeCode(newCode: String) = viewModelScope.launch {
        sharedPreferences.setStringElement(EXCHANGE_BASE_KEY, newCode)
    }

    fun onAmountChange(newValue: Double) = viewModelScope.launch {
        multiplexer = newValue
        if (!context.isConnected()) exchangeRateRepository.refreshData()
    }

    private fun scheduleWork() {
        workManager.cancelAllWork()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<RefreshListWork>()
            .setConstraints(constraints)
            .build()

        workManager.enqueue(request)
    }

    override fun onCleared() {
        super.onCleared()
        workManager.cancelAllWork()
    }

}