package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class TradeHistory(
    val type: String,
    val date: Long,
    val amount: Double,
    val price: Double,
    val tid: Long
)
