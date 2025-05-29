package eu.codlab.cex.spot.trading.groups.fees

import kotlinx.serialization.Serializable

@Serializable
data class FeeVolume(
    val fee: Double,
    val volume: Int
)
