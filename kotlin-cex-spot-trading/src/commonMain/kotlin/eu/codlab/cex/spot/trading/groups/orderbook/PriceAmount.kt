package eu.codlab.cex.spot.trading.groups.orderbook

import kotlinx.serialization.Serializable

@Serializable
data class PriceAmount(
    /**
     * indicates price level of the Order Book entry
     */
    val price: Double,
    /**
     * indicates amount of the Order Book entry
     */
    val amount: Double
)
