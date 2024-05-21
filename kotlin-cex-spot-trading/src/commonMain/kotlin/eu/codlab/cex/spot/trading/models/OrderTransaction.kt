package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderTransaction(
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
    val id: Long,
    val next: Boolean,
    val vtx: List<OrderInternalTransaction>
    // missing a:SYMBOL:c, a:SYMBOL:s, a:SYMBOL:d
)

@Serializable
data class OrderInternalTransaction(
    val id: Long,
    val type: OrderType,
    val time: String,
    val user: String,
    val c: String,
    val d: String,
    val a: String,
    val amount: Double,
    val balance: Double,
    val symbol: String,
    val order: Long,
    //val buy:
    //val sell:
    //val pair:
    //val pos:
    //val office:
    val cs: Double,
    val ds: Double
)
