package eu.codlab.cex.spot.trading.groups.processing

import kotlinx.serialization.Serializable

@Serializable
data class ProcessingInfo(
    /**
     * Cryptocurrency name.
     */
    val name: String,
    /**
     * This object contains info about all supported blockchains to deposit\withdraw cryptocurrency YYY.
     */
    val blockchains: Map<String, Blockchain>
)
