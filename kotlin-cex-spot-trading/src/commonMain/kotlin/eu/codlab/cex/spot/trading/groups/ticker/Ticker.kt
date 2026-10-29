package eu.codlab.cex.spot.trading.groups.ticker

import eu.codlab.cex.spot.trading.models.PercentageFloat
import kotlinx.serialization.Serializable

/**
 * CEX.IO can leave out any of these fields, they are null then
 */
@Serializable
data class Ticker(
    /**
     * Current highest buy order price (bestBid) in Order Book.
     */
    val bestBid: Double? = null,
    /**
     * Current lowest sell order price (bestAsk) in Order Book.
     */
    val bestAsk: Double? = null,
    /**
     * Last 24h bestBid price change in quote currency.
     */
    val bestBidChange: Double? = null,
    /**
     * Last 24h bestBid price change in percentage.
     */
    val bestBidChangePercentage: PercentageFloat? = null,
    /**
     * Last 24h bestAsk price change in quote currency.
     */
    val bestAskChange: Double? = null,
    /**
     * Last 24h bestAsk price change in percentage.
     */
    val bestAskChangePercentage: PercentageFloat? = null,
    /**
     * Last 30 days trading volume in base currency.
     */
    val volume30d: Double? = null,
    /**
     * Last 24h lowest trade price.
     */
    val low: Double? = null,
    /**
     * Last 24h highest trade price.
     */
    val high: Double? = null,
    /**
     * Last 24 hours volume in base currency.
     */
    val volume: Double? = null,
    /**
     * Last 24 hours volume in quote currency.
     */
    val quoteVolume: Double? = null,
    /**
     * Last trade volume in base currency.
     */
    val lastTradeVolume: Double? = null,
    /**
     * Last indicative price.
     */
    val last: Double? = null,
    /**
     * Last trade price in CEX.IO Ecosystem.
     */
    val lastTradePrice: Double? = null,
    /**
     * Last 24h price change in quote currency.
     */
    val priceChange: Double? = null,
    /**
     * Last 24h price change in percentage.
     */
    val priceChangePercentage: Double? = null,
    /**
     * Date & Time of last trade in ISO format. (YYYY-MM-DDTHH:mm:ss.SSSZ).
     */
    val lastTradeDateISO: String? = null,
    /**
     * Last 24h volume equivalent in USD currency.
     */
    val volumeUSD: Double? = null
)
