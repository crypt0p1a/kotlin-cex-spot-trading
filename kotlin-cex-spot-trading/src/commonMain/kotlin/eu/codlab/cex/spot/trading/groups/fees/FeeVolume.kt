package eu.codlab.cex.spot.trading.groups.fees

import kotlinx.serialization.Serializable

@Serializable
data class FeeVolume(
    /**
     * Fee percent which will be applied, if Client's 30d total trading volume up to the value,
     * indicated in "volume" of this object.
     */
    val fee: Double,
    /**
     * Represents Client's 30d trading volume (by default in USD), up to which "fee" value is
     * applied. For receiving current 30d trading volume Client should use Volume method.
     */
    val volume: Int
)
