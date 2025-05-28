package eu.codlab.cex.spot.trading.groups.candles

import korlibs.time.DateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class GetCandles(
    /**
     * Trading pair, for which Client wants to receive historical OHLCV candles. Trading pair should contain two currencies in uppercase divided by “-“ symbol. Pair should be listed in traditional direction. For example “BTC-USD”, but not “USD-BTC”. If this field is present and contains valid value, then it means Client wants to receive OHLCV candles for one specific trading pair. If "pair" field is present, then "pairsList" field should be absent. Either "pair" or "pairsList" should be indicated in the request anyway.
     */
    val pair: String? = null,
    /**
     * Array with trading pairs, for which Client wants to receive last historical OHLCV candles. At least 1 trading pair should be indicated in this field. If this field is present and contains valid values, then it means Client wants to receive last OHLCV candle for each indicated trading pair in the list. If "pairsList" field is present, then "pair" field should be absent. Either "pairsList" or "pair" should be indicated in the request anyway.
     */
    val pairsList: List<String>? = null,
    /**
     * The starting moment of time of the requested period for which OHLCV candles should be returned - UTC timestamp in milliseconds. If this field is present and contains valid value, then it means Client wants to receive OHLCV candles, the first one of which includes indicated moment of time. Either "fromISO" or "toISO" should be indicated in the request anyway.
     */
    val fromISO: Long? = null,
    /**
     * The last moment of time of the requested period for which OHLCV candles should be returned - UTC timestamp in milliseconds. If this field is present and contains valid value, then it means Client wants to receive OHLCV candles, the last one of which includes indicated moment of time. Either "fromISO" or "toISO" should be indicated in the request anyway.
     */
    val toISO: Long? = null,
    /**
     * Maximum number of OHLCV candles to be returned in response. Indicated number should be greater than zero. This field is mandatory if at least one of “fromISO” or “toISO” fields is specified in request. This field should be absent if both “fromISO” and “toISO” are specified in request. If “pairsList” field is specified in the request, then the value of this field should equal 1 (only last candle for each requested trading pair will be returned in response).
     */
    val limit: Int?,
    /**
     * The type of data, on the basis of which returned OHLC prices in candles should be calculated. Allowed values: “bestAsk”, “bestBid”.
     */
    val dataType: DataType,
    /**
     * Timeframe from which OHLCV candles data should be calculated. Allowed values: “1m”, “5m”, “15m”, “30m”, "1h", “2h”, “4h”, “1d”.
     */
    val resolution: CandleResolution
)

sealed class Candles {
    internal abstract fun toGetCandles(): GetCandles
}

data class CandlesFromPairs(
    /**
     * Array with trading pairs, for which Client wants to receive last historical OHLCV candles. At least 1 trading pair should be indicated in this field. If this field is present and contains valid values, then it means Client wants to receive last OHLCV candle for each indicated trading pair in the list. If "pairsList" field is present, then "pair" field should be absent. Either "pairsList" or "pair" should be indicated in the request anyway.
     */
    val pairsList: List<String>,
    /**
     * The starting moment of time of the requested period for which OHLCV candles should be returned - UTC timestamp in milliseconds. If this field is present and contains valid value, then it means Client wants to receive OHLCV candles, the first one of which includes indicated moment of time. Either "fromISO" or "toISO" should be indicated in the request anyway.
     */
    val fromISO: DateTime?,
    /**
     * The last moment of time of the requested period for which OHLCV candles should be returned - UTC timestamp in milliseconds. If this field is present and contains valid value, then it means Client wants to receive OHLCV candles, the last one of which includes indicated moment of time. Either "fromISO" or "toISO" should be indicated in the request anyway.
     */
    val toISO: DateTime?,
    /**
     * The type of data, on the basis of which returned OHLC prices in candles should be calculated. Allowed values: “bestAsk”, “bestBid”.
     */
    val dataType: DataType,
    /**
     * Timeframe from which OHLCV candles data should be calculated. Allowed values: “1m”, “5m”, “15m”, “30m”, "1h", “2h”, “4h”, “1d”.
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

data class CandlesFromPair(
    // val pair: String? = null,
    /**
     * Trading pair, for which Client wants to receive historical OHLCV candles. Trading pair should contain two currencies in uppercase divided by “-“ symbol. Pair should be listed in traditional direction. For example “BTC-USD”, but not “USD-BTC”. If this field is present and contains valid value, then it means Client wants to receive OHLCV candles for one specific trading pair. If "pair" field is present, then "pairsList" field should be absent. Either "pair" or "pairsList" should be indicated in the request anyway.
     *
     */
    val pair: String,
    /**
     * The starting moment of time of the requested period for which OHLCV candles should be returned - UTC timestamp in milliseconds. If this field is present and contains valid value, then it means Client wants to receive OHLCV candles, the first one of which includes indicated moment of time. Either "fromISO" or "toISO" should be indicated in the request anyway.
     */
    val fromISO: DateTime?,
    /**
     * The last moment of time of the requested period for which OHLCV candles should be returned - UTC timestamp in milliseconds. If this field is present and contains valid value, then it means Client wants to receive OHLCV candles, the last one of which includes indicated moment of time. Either "fromISO" or "toISO" should be indicated in the request anyway.
     */
    val toISO: DateTime?,
    /**
     * Maximum number of OHLCV candles to be returned in response. Indicated number should be greater than zero. This field is mandatory if at least one of “fromISO” or “toISO” fields is specified in request. This field should be absent if both “fromISO” and “toISO” are specified in request. If “pairsList” field is specified in the request, then the value of this field should equal 1 (only last candle for each requested trading pair will be returned in response).
     */
    val limit: Int?,
    /**
     * The type of data, on the basis of which returned OHLC prices in candles should be calculated. Allowed values: “bestAsk”, “bestBid”.
     */
    val dataType: DataType,
    /**
     * Timeframe from which OHLCV candles data should be calculated. Allowed values: “1m”, “5m”, “15m”, “30m”, "1h", “2h”, “4h”, “1d”.
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

@Serializable
data class Candle(
    /**
     * OHLCV candle timestamp - UTC timestamp in milliseconds.
     */
    val timestamp: Long,
    /**
     * Opening price of OHLCV candle in quote currency.
     */
    val open: String? = null,
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
     * Timeframe from which OHLCV candles data should be calculated. Allowed values: “1m”, “5m”, “15m”, “30m”, "1h", “2h”, “4h”, “1d”.
     */
    val resolution: CandleResolution,
    /**
     * Indicates whether the specific candle is currently closed. If the value of this field is true, then it means this candle has been already closed. If this field is absent, then it means this candle is still open.
     */
    val isClosed: Boolean? = null,
    /**
     * OHLCV candle date & time in ISO format (“YYYY-MM-DDTHH:mm:ss.SSSZ").
     */
    val timestampISO: String,
)

@Serializable
enum class CandleResolution {
    @SerialName("1m")
    Res1m,

    @SerialName("5m")
    Res5m,

    @SerialName("15m")
    Res15m,

    @SerialName("30m")
    Res30m,

    @SerialName("1h")
    Res1h,

    @SerialName("2h")
    Res2h,

    @SerialName("4h")
    Res4h,

    @SerialName("1d")
    Res1d
}

@Serializable
enum class DataType {
    @SerialName("bestAsk")
    BestAsk,

    @SerialName("bestBid")
    BestBid
}