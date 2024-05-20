package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyLimit(
    val symbol1: String,
    val symbol2: String,
    val pricePrecision: String,
    val minLotSize: Double,
    val minLotSizeS2: Double,
    val maxLotSize: Double?,
    val minPrice: Double,
    val maxPrice: Double,
)
