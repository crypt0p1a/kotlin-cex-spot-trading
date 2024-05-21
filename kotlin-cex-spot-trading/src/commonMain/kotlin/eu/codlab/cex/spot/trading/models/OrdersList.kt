package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
internal data class OrdersList(
    val orders_list: List<Long>
)
