package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenOrder(
    val id: Long,
    val time: Long,
    val type: OrderType,
    val price: Double,
    val amount: Double,
    val pending: Double
)

@Serializable
enum class OrderType {
    @SerialName("buy")
    Buy,

    @SerialName("sell")
    Sell
}
