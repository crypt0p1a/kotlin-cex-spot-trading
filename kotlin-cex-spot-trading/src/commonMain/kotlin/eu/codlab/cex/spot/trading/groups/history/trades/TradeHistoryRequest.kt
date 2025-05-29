package eu.codlab.cex.spot.trading.groups.history.trades

import eu.codlab.cex.spot.trading.groups.orders.OrderSide
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TradeHistoryRequestWithTrade(
    /**
     * Trading pair, for which Client wants to receive trades history. Trading pair should contain
     * two currencies in uppercase divided by “-“ symbol. Pair should be listed in traditional
     * direction. For example “BTC-USD”, but not “USD-BTC”.
     */
    val pair: String,
    /**
     * Side of requested trades. If this field is present, it should contain only one of allowed
     * values: “BUY” or “SELL”. If this field is not indicated in the request, then response would
     * contain trades for "BUY" and "SELL" sides.
     */
    val side: OrderSide? = null,
    /**
     * Unique trade identifier (tradeId) in CEX.IO Spot Trading system, starting from which
     * subsequent trades should be returned in response. If this field is present, then
     * “fromDateISO” and “toDateISO” fields should not be indicated in the request.
     */
    val fromTradeId: String? = null,
    /**
     * Unique trade identifier (tradeId) in CEX.IO Spot Trading system, which should be the last
     * trade returned in response. If this field is present, then “fromTradeId” should also be
     * present, “fromDateISO” and “toDateISO” fields should not be indicated in the request.
     */
    val toTradeId: String? = null,
    /**
     * Maximum number of trades, which should be returned in response. If this field is present then
     * the value should be more than zero and not more than 1000. If indicated value is more than
     * 1000, the response will still contain only up to 1000 trades max. If this field is not
     * specified in request, then by default 100 trades would be returned in response.
     */
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
    /**
     * Trading pair, for which Client wants to receive trades history. Trading pair should contain
     * two currencies in uppercase divided by “-“ symbol. Pair should be listed in traditional
     * direction. For example “BTC-USD”, but not “USD-BTC”.
     */
    val pair: String,
    /**
     * Side of requested trades. If this field is present, it should contain only one of allowed
     * values: “BUY” or “SELL”. If this field is not indicated in the request, then response would
     * contain trades for "BUY" and "SELL" sides.
     */
    val side: OrderSide? = null,
    /**
     * The starting moment of time of the requested period in ISO format (YYYY-MM-DDTHH:mm:ss.SSSZ)
     * for which trades are requested. If “fromDateISO” and “pageSize” parameters are not specified
     * in request, then by default last 1000 trades will be returned in response. If this field is
     * present, then “fromTradeId” and “toTradeId” fields should not be indicated in the request.
     */
    val fromDateISO: String? = null,
    /**
     * The last moment of time of the requested period in ISO format (YYYY-MM-DDTHH:mm:ss.SSSZ) for
     * which trades are requested. If this field is present, then “fromDateISO” should also be
     * present, “fromTradeId” and “toTradeId” fields should be absent.
     */
    val toDateISO: String? = null,
    /**
     * Maximum number of trades, which should be returned in response. If this field is present then
     * the value should be more than zero and not more than 1000. If indicated value is more than
     * 1000, the response will still contain only up to 1000 trades max. If this field is not
     * specified in request, then by default 100 trades would be returned in response.
     */
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
    val side: OrderSide? = null,
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
