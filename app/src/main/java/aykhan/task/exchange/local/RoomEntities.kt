package aykhan.task.exchange.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchangeRates")
data class ExchangeRate (
    @PrimaryKey
    val code: String,
    val alphaCode: String,
    val numericCode: String,
    val name: String,
    var rate: Double,
    val date: String,
    val inverseRate: Double
)