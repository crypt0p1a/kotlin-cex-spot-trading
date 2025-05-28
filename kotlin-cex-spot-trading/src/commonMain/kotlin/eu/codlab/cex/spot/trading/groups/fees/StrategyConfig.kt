package eu.codlab.cex.spot.trading.groups.fees

import kotlinx.serialization.Serializable

@Serializable
data class StrategyConfig(
    val default: List<FeeVolume>
)
