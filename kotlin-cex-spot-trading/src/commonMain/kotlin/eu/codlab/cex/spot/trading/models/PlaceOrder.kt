package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class PlaceOrder(
    val type: OrderType,
    val amount: Double,
    val price: Double
)
