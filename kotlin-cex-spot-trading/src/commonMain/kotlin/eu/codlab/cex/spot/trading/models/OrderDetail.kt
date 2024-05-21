package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDetail(
    val user: String,
    val type: OrderType,
    val symbol1: String,
    val symbol2: String,
    val amount: Double,
    val remains: Double,
    val price: Double,
    val time: Long,
    val lastTxTime: Long,
    val tradingFeeStrategy: String,
    val tradingFeeTaker: Double,
    val tradingFeeMaker: Double,
    val tradingFeeUserVolumeAmount: Double,
    @SerialName("ordType")
    val orderKind: OrderKind,
    @SerialName("kind")
    val origin: String, // api
    val lastTx: Long,
    val status: String,
    val orderId: Long,
    val id: Long
    // missing a:SYMBOL:c, a:SYMBOL:s, a:SYMBOL:d
)

@Serializable
enum class OrderKind {
    @SerialName("market")
    Market,

    @SerialName("limit")
    Limit
}