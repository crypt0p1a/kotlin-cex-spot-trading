package eu.codlab.cex.spot.trading.groups.candles

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
