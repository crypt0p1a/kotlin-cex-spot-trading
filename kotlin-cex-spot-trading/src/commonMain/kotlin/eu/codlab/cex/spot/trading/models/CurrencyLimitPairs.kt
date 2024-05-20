package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyLimitPairs(
    val pairs: List<CurrencyLimit>
)
