package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class Pairs(
    val pairs: List<String>
)
