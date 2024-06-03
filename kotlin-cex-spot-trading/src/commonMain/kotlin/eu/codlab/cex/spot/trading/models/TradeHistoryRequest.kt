package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TradeHistoryRequestWithTrade(
    val pair: String,
    val side: OrderType? = null,
    val fromTradeId: String? = null,
    val toTradeId: String? = null,
    val pageSize: Int? = null
) {
    internal fun to() = TradeHistoryRequest(
        pair = pair,
        side = side,
        fromTradeId = fromTradeId,
        toTradeId = toTradeId,
        pageSize = pageSize
    )
}

@Serializable
data class TradeHistoryRequestWithDate(
    val pair: String,
    val side: OrderType? = null,
    val fromDateISO: String? = null,
    val toDateISO: String? = null,
    val pageSize: Int? = null
) {
    internal fun to() = TradeHistoryRequest(
        pair = pair,
        side = side,
        fromDateISO = fromDateISO,
        toDateISO = toDateISO,
        pageSize = pageSize
    )
}

@Serializable
internal data class TradeHistoryRequest(
    val pair: String,
    val side: OrderType? = null,
    val fromDateISO: String? = null,
    val toDateISO: String? = null,
    val fromTradeId: String? = null,
    val toTradeId: String? = null,
    val pageSize: Int? = null
)

@Serializable
enum class OrderTypeHistory {
    @SerialName("BUY")
    Buy,

    @SerialName("SELL")
    Sell
}