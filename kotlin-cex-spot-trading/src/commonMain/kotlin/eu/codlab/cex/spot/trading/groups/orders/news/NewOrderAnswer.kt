package eu.codlab.cex.spot.trading.groups.orders.news

import eu.codlab.cex.spot.trading.groups.orders.OrderSide
import eu.codlab.cex.spot.trading.groups.orders.OrderType
import eu.codlab.cex.spot.trading.models.OrderStatus
import korlibs.time.DateTime
import kotlinx.serialization.Serializable

@Serializable
data class NewOrderAnswer(
    val clientId: String,
    val accountId: String,
    val orderId: String,
    val clientOrderId: String,
    val status: OrderStatus,
    val currency1: String,
    val currency2: String,
    val side: OrderSide,
    val orderType: OrderType,
    val timestamp: Long = DateTime.nowUnixMillisLong(),
    // val timeInForce: String,
    /**
     * 255 chars max
     */
    val comment: String,
    val orderRejectReason: String? = null,
    val rejectCode: Int? = null,
    val rejectReason: String? = null,
    /**
     * Represents executed amount in currency1. If this value is 0, then it means there is no executed amount (order has no executions).
     */
    val executedAmountCcy1: String,
    /**
     * Represents executed amount in currency2. If this value is 0, then it means there is no executed amount (order has no executions).
     */
    val executedAmountCcy2: String,
    /**
     * Represents order amount in currency1, which was requested by Client. If this value is null, then it means there is no requested amount in currency1 (order should have then requested amount in currency2)
     */
    val requestedAmountCcy1: String,
    /**
     * Represents order amount in currency2, which was requested by Client. If this value is null, then it means there is no requested amount in currency2 (order should have then requested amount in currency1).
     */
    val requestedAmountCcy2: String,
    /**
     * String representation of the amount in right
     */
    val amountCcy2: String,
    /**
     * Represents order commission amount, which was charged for this order . If this value is 0, then it means there is no commission amount charged for this order so far.
     */
    val feeAmount: String,
    /**
     * Represents order commission currency, in which feeAmount is calculated.
     */
    val feeCurrency: String,
    /**
     * String representation of the price
     */
    val price: String,
    /**
     * Represents average order execution price. If this value is null, then it means there is no executed amount (order has no executions).
     */
    val averagePrice: String? = null,
    /**
     * UTC timestamp in milliseconds. If Expire Time is in the past, order will be rejected.
     */
    val expireTime: Long? = null,
    /**
     * UTC timestamp in milliseconds. Represents an effective timestamp provided by Client during creation of the order. If this value is null, then it means that Client did not provide effective time during order creation.
     */
    val effectiveTime: Long? = null
)