package eu.codlab.cex.spot.trading.groups.ticker

import eu.codlab.cex.spot.trading.models.PercentageFloat
import kotlinx.serialization.Serializable

@Serializable
data class Ticker(
    /**
     * Current highest buy order price (bestBid) in Order Book.
     */
    val bestBid: Double,
    /**
     * Current lowest sell order price (bestAsk) in Order Book.
     */
    val bestAsk: Double,
    /**
     * Last 24h bestBid price change in quote currency.
     */
    val bestBidChange: Double,
    /**
     * Last 24h bestBid price change in percentage.
     */
    val bestBidChangePercentage: PercentageFloat,
    /**
     * Last 24h bestAsk price change in quote currency.
     */
    val bestAskChange: Double,
    /**
     * Last 24h bestAsk price change in percentage.
     */
    val bestAskChangePercentage: PercentageFloat,
    /**
     * Last 30 days trading volume in base currency.
     */
    val volume30d: Double,
    /**
     * Last 24h lowest trade price.
     */
    val low: Double,
    /**
     * Last 24h highest trade price.
     */
    val high: Double,
    /**
     * Last 24 hours volume in base currency.
     */
    val volume: Double,
    /**
     * Last 24 hours volume in quote currency.
     */
    val quoteVolume: Double,
    /**
     * Last trade volume in base currency.
     */
    val lastTradeVolume: Double,
    /**
     * Last indicative price.
     */
    val last: Double,
    /**
     * Last trade price in CEX.IO Ecosystem.
     */
    val lastTradePrice: Double,
    /**
     * Last 24h price change in quote currency.
     */
    val priceChange: Double,
    /**
     * Last 24h price change in percentage.
     */
    val priceChangePercentage: Double,
    /**
     * Date & Time of last trade in ISO format. (YYYY-MM-DDTHH:mm:ss.SSSZ).
     */
    val lastTradeDateISO: String,
    /**
     * Last 24h volume equivalent in USD currency.
     */
    val volumeUSD: Double
)
