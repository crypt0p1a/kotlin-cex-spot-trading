package eu.codlab.cex.spot.trading.groups.orders

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class OrderSide {
    @SerialName("BUY")
    Buy,

    @SerialName("SELL")
    Sell
}