package eu.codlab.cex.spot.trading.groups.candles

import eu.codlab.cex.spot.trading.models.DataType
import korlibs.time.DateTime

data class CandlesFromPairs(
    /**
     * Array with trading pairs, for which Client wants to receive last historical OHLCV candles.
     * At least 1 trading pair should be indicated in this field. If this field is present and
     * contains valid values, then it means Client wants to receive last OHLCV candle for each
     * indicated trading pair in the list. If "pairsList" field is present, then "pair" field
     * should be absent. Either "pairsList" or "pair" should be indicated in the request anyway.
     */
    val pairsList: List<String>,
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
        pairsList = pairsList,
        fromISO = fromISO?.unixMillisLong,
        toISO = toISO?.unixMillisLong,
        limit = 1,
        dataType = dataType,
        resolution = resolution
    )
}
