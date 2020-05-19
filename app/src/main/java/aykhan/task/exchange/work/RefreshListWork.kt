package aykhan.task.exchange.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import aykhan.task.exchange.local.getDatabase
import aykhan.task.exchange.manager.PreferencesManager
import aykhan.task.exchange.repository.ExchangeRateRepository
import aykhan.task.exchange.utils.DEFAULT_EXCHANGE_BASE
import aykhan.task.exchange.utils.EXCHANGE_BASE_KEY

class RefreshListWork(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = try {
        val database = getDatabase(applicationContext)
        val sharedPreferences = PreferencesManager.getInstance(applicationContext)
        val repository = ExchangeRateRepository.getInstance(database)

        repository.getExchangeRates(sharedPreferences.getStringElement(
            EXCHANGE_BASE_KEY, DEFAULT_EXCHANGE_BASE
            ) ?: DEFAULT_EXCHANGE_BASE
        )
        Result.success()
    } catch (e: Exception) {
        Result.retry()
    }

}