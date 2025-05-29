package eu.codlab.cex.spot.trading.groups.orders.cancel

import kotlinx.serialization.Serializable

@Serializable
data class CancelOrders(
    val clientOrderIds: List<String>
)
