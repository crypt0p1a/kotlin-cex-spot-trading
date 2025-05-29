package eu.codlab.cex.spot.trading.groups.pairsinfo

import kotlinx.serialization.Serializable

@Serializable
data class PairInfo(
    /**
     * Name of the first (base) currency in trading pair.
     */
    val base: String,
    /**
     * Name of the first (base) currency in trading pair.
     */
    val quote: String,
    /**
     * Minimum order amount in base currency.
     */
    val baseMin: Double,
    /**
     * Maximum order amount in base currency.
     */
    val baseMax: Double,
    /**
     * Order lot size in base currency. Such limitation allows only order amounts that are a
     * multiple of baseLotSize. For example, if trading pair baseLotSize is 0.5, then order amounts
     * 1.5, 4, 10.5 will be accepted, while amounts 0.3, 1.2, 10.9 will be rejected by the system.
     */
    val baseLotSize: Double,
    /**
     * Minimum order amount in quote currency.
     */
    val quoteMin: Double,
    /**
     * Minimum order amount in quote currency.
     */
    val quoteMax: Double,
    /**
     * Order lot size in quote currency. Such limitation allows only order amounts that are a
     * multiple of quote lot size. For example, if trading pair quoteLotSize is 0.01, then order
     * amounts 0.5, 4, 10.59 will be accepted, while amounts 0.313, 1.2987, 1000.989 will be
     * rejected by the system.
     */
    val quoteLotSize: Double,
    /**
     * Number of decimal places for the base currency executed amounts, used inside
     * CEX.IO Spot Trading system.
     */
    val basePrecision: Int,
    /**
     * Number of decimal places for the quote currency executed amounts, used inside
     * CEX.IO Spot Trading system.
     */
    val quotePrecision: Int,
    /**
     * Number of allowed decimal places for the trading pair price. Such limitation allows only
     * order prices, the number of decimal places in which doesn’t exceed pricePrecision value. For
     * example, if trading pair pricePrecision is 3, then order limit prices 0.3, 1.94, 10,
     * 10348.591 will be accepted, while prices 0.3136, 1.2987, 1000.98981234 will be rejected by
     * the system.
     */
    val pricePrecision: Int,
    /**
     * Minimum allowed trading pair price. Orders with indication of prices, which are lower than
     * specified value will be rejected by the system.
     */
    val minPrice: Double,
    /**
     * Maximum allowed trading pair price. Orders with indication of prices, which are higher than
     * specified value will be rejected by the system.
     */
    val maxPrice: Double
)
