package eu.codlab.cex.spot.trading.groups.volume

import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    val period: String,
    val volume: Double,
    val currency: String
)