package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Candle(
    val timestamp: Long,
    val open: Double? = null,
    val high: Double? = null,
    val low: Double? = null,
    val close: Double? = null,
    val volume: Long? = null,
    val resolution: CandleResolution,
    val isClosed: Boolean = false,
    val timestampISO: String
)

@Serializable
data class CandlePairs(
    @SerialName("pairsList")
    val pairs: List<String>,
    @SerialName("fromISO")
    val fromISOMs: Long? = null,
    @SerialName("toISO")
    val toISOMs: Long? = null,
    val dataType: CandleDataType = CandleDataType.BestAsk,
    val resolution: CandleResolution = CandleResolution.Res1m
) {
    internal fun to() = CandlePairsInternal(
        pairs,
        fromISOMs,
        toISOMs,
        limit = 1,
        dataType,
        resolution
    )
}

@Serializable
internal data class CandlePairsInternal(
    @SerialName("pairsList")
    val pairs: List<String>,
    @SerialName("fromISO")
    val fromISOMs: Long? = null,
    @SerialName("toISO")
    val toISOMs: Long? = null,
    val limit: Int = 1, // needs to be set explicitly to 1
    val dataType: CandleDataType = CandleDataType.BestAsk,
    val resolution: CandleResolution = CandleResolution.Res1m
)

@Serializable
data class CandleSinglePair(
    val pair: String,
    @SerialName("fromISO")
    val fromISOMs: Long? = null,
    @SerialName("toISO")
    val toISOMs: Long? = null,
    val limit: Int = 1,
    val dataType: CandleDataType = CandleDataType.BestAsk,
    val resolution: CandleResolution = CandleResolution.Res1m
)

@Serializable
enum class CandleDataType {
    @SerialName("bestAsk")
    BestAsk,

    @SerialName("bestBid")
    BestBid
}

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