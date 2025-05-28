package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderResult(
    val orderId: String,
    val clientOrderId: String,
    val clientId: String,
    val accountId: String? = null,
    val status: OrderStatus,
    val statusIsFinal: Boolean,
    val currency1: String,
    val currency2: String,
    val side: String,
    val orderType: String,
    val timeInForce: String,
    val comment: String? = null,
    val rejectCode: Int? = null,
    val rejectReason: String? = null,
    val executedAmountCcy1: Double? = null,
    val executedAmountCcy2: Double? = null,
    val requestedAmountCcy1: Double? = null,
    val requestedAmountCcy2: Double? = null,
    val initialOnHoldAmountCcy1: Double? = null,
    val initialOnHoldAmountCcy2: Double? = null,
    val feeAmount: Double? = null,
    val feeCurrency: String? = null,
    val price: Double? = null,
    val averagePrice: Double? = null,
    val clientCreateTimestamp: Long,
    val serverCreateTimestamp: Long,
    val lastUpdateTimestamp: Long,
    val expireTime: Long,
    val effectiveTime: Long,
)

@Serializable
enum class OrderStatus {
    PENDING_NEW,
    NEW,
    PARTIALLY_FILLED,
    FILLED,
    EXPIRED,
    REJECTED,
    PENDING_CANCEL,
    CANCELLED
}