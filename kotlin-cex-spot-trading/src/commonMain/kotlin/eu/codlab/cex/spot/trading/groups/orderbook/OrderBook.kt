package eu.codlab.cex.spot.trading.groups.orderbook

import kotlinx.serialization.Serializable

@Serializable
data class OrderBook(
    /**
     * Order book snapshot server time - UTC timestamp in milliseconds.
     */
    val timestamp: Long,
    /**
     * The first currency in the requested trading pair.
     */
    val currency1: String,
    /**
     * The second currency in the requested trading pair.
     */
    val currency2: String,
    /**
     * This array contains a list of bids of the order book. The first value of an array element
     * indicates price level of the Order Book entry, the second value of an array element indicates
     * amount of the Order Book entry. The value in this field can be an empty array in case of no
     * bids are available in the Order Book.
     */
    @Serializable(with = PriceAmountSerializer::class)
    val bids: List<PriceAmount>,
    /**
     * This array contains a list of asks of the Order Book. The first value of an array element
     * indicates price level of the Order Book entry, the second value of an array element indicates
     * amount of the Order Book entry. The value in this field can be an empty array in case of no
     * asks are available in the Order Book.
     */
    @Serializable(with = PriceAmountSerializer::class)
    val asks: List<PriceAmount>
)
