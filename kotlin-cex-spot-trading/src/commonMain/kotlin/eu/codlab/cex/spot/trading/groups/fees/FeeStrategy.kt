package eu.codlab.cex.spot.trading.groups.fees

import kotlinx.serialization.Serializable

@Serializable
data class FeeStrategy(
    /**
     * This object defines details about Client's available fee options.
     */
    val strategyConfig: StrategyConfig
    // TODO perTier
    // TODO perWeekend
)
