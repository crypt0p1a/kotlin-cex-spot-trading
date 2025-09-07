package eu.codlab.cex.spot.trading.groups.candles

import kotlinx.serialization.Serializable

@Serializable
data class Candle(
    /**
     * OHLCV candle timestamp - UTC timestamp in milliseconds.
     */
    val timestamp: Long,
    /**
     * Opening price of OHLCV candle in quote currency.
     */
    val open: Double? = null,
    /**
     * Highest price of OHLCV candle in quote currency, which was reached during candle timeframe.
     */
    val high: Double? = null,
    /**
     * Lowest price of OHLCV candle in quote currency, which was reached during candle timeframe.
     */
    val low: Double? = null,
    /**
     * Closing price of OHLCV candle in quote currency.
     */
    val close: Double? = null,
    /**
     * Quantity of price changes within period.
     */
    val volume: Double? = null,
    /**
     * Timeframe from which OHLCV candles data should be calculated. Allowed values:
     * - “1m”,
     * - “5m”,
     * - “15m”,
     * - “30m”,
     * - "1h",
     * - “2h”,
     * - “4h”,
     * - “1d”.
     */
    val resolution: CandleResolution,
    /**
     * Indicates whether the specific candle is currently closed. If the value of this field is
     * true, then it means this candle has been already closed. If this field is absent, then it
     * means this candle is still open.
     */
    val isClosed: Boolean? = null,
    /**
     * OHLCV candle date & time in ISO format (“YYYY-MM-DDTHH:mm:ss.SSSZ").
     */
    val timestampISO: String,
)
