package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LastPrice(
    @SerialName("lprice")
    val lastPrice: Double,
    @SerialName("curr1")
    val symbol1: String,
    @SerialName("curr2")
    val symbol2: String,
)

@Serializable
data class LastPriceCorrect(
    @SerialName("lprice")
    val lastPrice: Double,
    val symbol1: String,
    val symbol2: String,
) {
    fun to() = LastPrice(
        lastPrice,
        symbol1,
        symbol2
    )
}
