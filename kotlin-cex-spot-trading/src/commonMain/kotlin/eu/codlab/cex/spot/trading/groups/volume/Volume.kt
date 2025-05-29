package eu.codlab.cex.spot.trading.groups.volume

import kotlinx.serialization.Serializable

@Serializable
data class Volume(
    /**
     * Represents period for which Client's volume is calculated. Only “30d” value can be returned
     * in this field.
     */
    val period: String,
    /**
     * Represents Client’s cumulative trading volume in “currency” equivalent for returned period.
     */
    val volume: Double,
    /**
     * Represents the currency in which trading volume is calculated. Only “USD” value can be
     * returned in this field.
     */
    val currency: String
)
