package aykhan.task.exchange

import android.app.Application
import timber.log.Timber

class ExchangeRateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}