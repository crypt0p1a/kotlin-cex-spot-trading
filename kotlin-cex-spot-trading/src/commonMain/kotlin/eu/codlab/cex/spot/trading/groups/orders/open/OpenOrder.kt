package eu.codlab.cex.spot.trading.groups.orders.open

import kotlinx.serialization.Serializable

@Serializable
data class OpenOrder(
    val orderId: String
)
