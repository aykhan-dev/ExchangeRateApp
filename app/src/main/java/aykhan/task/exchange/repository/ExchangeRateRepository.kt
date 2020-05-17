package aykhan.task.exchange.repository

import aykhan.task.exchange.local.AppDatabase
import aykhan.task.exchange.network.ApiInitHelper
import aykhan.task.exchange.network.NetworkState
import aykhan.task.exchange.utils.SingletonHolder
import aykhan.task.exchange.utils.asEntityObject
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext
import timber.log.Timber

class ExchangeRateRepository private constructor(private val database: AppDatabase) {

    companion object :
        SingletonHolder<ExchangeRateRepository, AppDatabase>(::ExchangeRateRepository)

    private val exchangeRateService by lazy { ApiInitHelper.exchangeRateService }

    val exchangeRates by lazy { database.exchangeRateDao.getVideos() }

    suspend fun getRates(base: String) = try {
        val response = exchangeRateService.fetchRates(base)
        response.body()?.let { list ->
            if (response.isSuccessful) withContext(Default) {
                val converted = list.asEntityObject()
                database.exchangeRateDao.insertAll(*converted)
            }
        }
        NetworkState.Success
    } catch (exception: Exception) {
        Timber.e("No Internet Connection or Unresolved Host Name")
        NetworkState.Failure
    }

}