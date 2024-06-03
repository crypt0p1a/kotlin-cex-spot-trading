package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class PairInfo(
    val base: String,
    val quote: String,
    val baseMin: Double,
    val baseMax: Double,
    val baseLotSize: Double,
    val quoteMin: Double,
    val quoteMax: Double,
    val quoteLotSize: Double,
    val basePrecision: Int,
    val quotePrecision: Int,
    val pricePrecision: Int,
    val minPrice: Double,
    val maxPrice: Double
)
