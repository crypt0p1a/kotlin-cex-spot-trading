package eu.codlab.cex.spot.trading.groups.orders

import kotlinx.serialization.Serializable

@Serializable
data class OrdersParam(
    val pair: String?
)