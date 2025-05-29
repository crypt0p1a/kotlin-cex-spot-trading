package eu.codlab.cex.spot.trading.groups.orderbook

import kotlinx.serialization.Serializable

@Serializable
data class OrderBookRequest(
    /**
     * Trading pair, for which Client wants to request an Order Book snapshot. Trading pair should
     * contain two currencies in uppercase divided by “-“ symbol. Pair should be listed in
     * traditional direction. For example “BTC-USD”, but not “USD-BTC”.
     */
    val pair: String
)
