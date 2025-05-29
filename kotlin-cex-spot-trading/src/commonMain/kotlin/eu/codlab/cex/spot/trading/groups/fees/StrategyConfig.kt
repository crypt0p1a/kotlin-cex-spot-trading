package eu.codlab.cex.spot.trading.groups.fees

import kotlinx.serialization.Serializable

@Serializable
data class StrategyConfig(
    /**
     * Represents possible fee levels, which are applied by default for pairs, if such pairs are not
     * specified in "perTier.pairList" field. If returned array contains only 1 object and zero
     * "volume" value, then "fee" value represents fixed commission, which does not depend on
     * Client's 30d trading volume.
     */
    val default: List<FeeVolume>
)
