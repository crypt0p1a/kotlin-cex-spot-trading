package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class TradeHistory(
    val pageSize: Int,
    val trades: List<TradeFromHistory>
)

@Serializable
data class TradeFromHistory(
    val side: OrderTypeHistory,
    val dateISO: String,
    val price: Double,
    val amount: Double,
    val tradeId: String
)