package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class Currencies(
    val currencies: List<String>
)
