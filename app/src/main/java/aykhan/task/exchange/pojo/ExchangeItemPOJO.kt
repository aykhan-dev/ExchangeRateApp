package aykhan.task.exchange.pojo

import com.squareup.moshi.Json

data class ExchangeItemPOJO(
    @Json(name = "code") val code: String,
    @Json(name = "alphaCode") val alphaCode: String,
    @Json(name = "numericCode") val numericCode: String,
    @Json(name = "name") val name: String,
    @Json(name = "rate") val rate: Double,
    @Json(name = "date") val date: String,
    @Json(name = "inverseRate") val inverseRate: Double
)