package aykhan.task.exchange.viewModel.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aykhan.task.exchange.local.ExchangeRate
import aykhan.task.exchange.local.getDatabase
import aykhan.task.exchange.repository.ExchangeRateRepository
import aykhan.task.exchange.utils.DEFAULT_EXCHANGE_BASE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class ExchangeRateViewModel(application: Application) : AndroidViewModel(application) {

    private val exchangeRateRepository = ExchangeRateRepository(getDatabase(application))

    private val _exchangeBase = MutableLiveData<String>().apply { value = DEFAULT_EXCHANGE_BASE }
    val exchangeBase: LiveData<String> get() = _exchangeBase

    var exchangeRates: LiveData<List<ExchangeRate>> = exchangeRateRepository.exchangeRates

    fun fetchExchangeRates(wait: Boolean) = viewModelScope.launch {
        if (wait) delay(1000)
        exchangeRateRepository.getRates(exchangeBase.value ?: DEFAULT_EXCHANGE_BASE)
    }

    fun updateExchangeCode(newCode: String) {
        _exchangeBase.value = newCode
    }

}