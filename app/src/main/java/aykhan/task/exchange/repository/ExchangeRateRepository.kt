package aykhan.task.exchange.repository

import aykhan.task.exchange.local.AppDatabase
import aykhan.task.exchange.network.ApiInitHelper
import aykhan.task.exchange.utils.asEntityObject
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.withContext

class ExchangeRateRepository(private val database: AppDatabase) {

    private val exchangeRateService by lazy { ApiInitHelper.exchangeRateService }

    val exchangeRates by lazy { database.exchangeRateDao.getVideos() }

    suspend fun getRates(base: String) {
        val response = exchangeRateService.fetchRates(base)
        response.body()?.let { list ->
            if (response.isSuccessful) withContext(Default) {
                val converted = list.asEntityObject()
                database.exchangeRateDao.insertAll(*converted)
            }
        }
    }

}