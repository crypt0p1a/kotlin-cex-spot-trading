package eu.codlab.cex.spot.trading.groups.processing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Describes current availability to withdraw cryptocurrency YYY via blockchain X. Only "enabled" or
 * "disabled" values are allowed herein.
 */
@Serializable
enum class Capability {
    @SerialName("enabled")
    Enabled,

    @SerialName("disabled")
    Disabled
}
