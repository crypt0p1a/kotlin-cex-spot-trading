package eu.codlab.cex.spot.trading.groups.fees

import kotlinx.serialization.Serializable

@Serializable
data class FeeStrategy(
    val strategyConfig: StrategyConfig
    // TODO perTier
    // TODO perWeekend
)
