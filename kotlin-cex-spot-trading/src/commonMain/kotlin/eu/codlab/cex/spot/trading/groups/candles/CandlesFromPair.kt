package eu.codlab.cex.spot.trading.groups.candles

import eu.codlab.cex.spot.trading.models.DataType
import korlibs.time.DateTime

data class CandlesFromPair(
    /**
     * Trading pair, for which Client wants to receive historical OHLCV candles. Trading pair should
     * contain two currencies in uppercase divided by “-“ symbol. Pair should be listed in
     * traditional direction. For example “BTC-USD”, but not “USD-BTC”. If this field is present and
     * contains valid value, then it means Client wants to receive OHLCV candles for one specific
     * trading pair. If "pair" field is present, then "pairsList" field should be absent.
     * Either "pair" or "pairsList" should be indicated in the request anyway.
     *
     */
    val pair: String,
    /**
     * The starting moment of time of the requested period for which OHLCV candles should be
     * returned - UTC timestamp in milliseconds. If this field is present and contains valid value,
     * then it means Client wants to receive OHLCV candles, the first one of which includes
     * indicated moment of time. Either "fromISO" or "toISO" should be indicated in the request
     * anyway.
     */
    val fromISO: DateTime?,
    /**
     * The last moment of time of the requested period for which OHLCV candles should be returned -
     * UTC timestamp in milliseconds. If this field is present and contains valid value, then it
     * means Client wants to receive OHLCV candles, the last one of which includes indicated moment
     * of time. Either "fromISO" or "toISO" should be indicated in the request anyway.
     */
    val toISO: DateTime?,
    /**
     * Maximum number of OHLCV candles to be returned in response. Indicated number should be
     * greater than zero. This field is mandatory if at least one of “fromISO” or “toISO” fields is
     * specified in request. This field should be absent if both “fromISO” and “toISO” are specified
     * in request. If “pairsList” field is specified in the request, then the value of this field
     * should equal 1 (only last candle for each requested trading pair will be returned in
     * response).
     */
    val limit: Int?,
    /**
     * The type of data, on the basis of which returned OHLC prices in candles should be calculated.
     * Allowed values: “bestAsk”, “bestBid”.
     */
    val dataType: DataType,
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
    val resolution: CandleResolution
) : Candles() {
    override fun toGetCandles() = GetCandles(
        pair = pair,
        fromISO = fromISO?.unixMillisLong,
        toISO = toISO?.unixMillisLong,
        limit = limit,
        dataType = dataType,
        resolution = resolution
    )
}
