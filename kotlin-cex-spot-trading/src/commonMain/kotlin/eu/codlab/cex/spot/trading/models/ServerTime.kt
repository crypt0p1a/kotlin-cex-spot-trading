package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class ServerTime(
    val timestamp: Double,
    val ISODate: String
)
