package eu.codlab.cex.spot.trading.groups.orders

import kotlinx.serialization.Serializable

@Serializable
data class EmptyBody(
    val empty: String? = null
)
