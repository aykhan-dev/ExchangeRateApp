package aykhan.task.exchange.utils

import android.content.Context
import android.net.ConnectivityManager
import aykhan.task.exchange.local.ExchangeRate
import aykhan.task.exchange.pojo.ExchangeItemPOJO

fun List<ExchangeItemPOJO>.asEntityObject() = map {
    ExchangeRate(
        code = it.code,
        alphaCode = it.alphaCode,
        numericCode = it.numericCode,
        name = it.name,
        rate = it.rate,
        date = it.date,
        inverseRate = it.inverseRate
    )
}.toTypedArray()

fun Context.isConnectedToNetwork(): Boolean? {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return connectivityManager?.isActiveNetworkMetered
}