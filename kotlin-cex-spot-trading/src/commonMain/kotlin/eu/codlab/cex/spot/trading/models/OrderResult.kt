package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

/**
 * This object contains list of orders which satisfy request criteria. It might be empty array ([]).
 * If this array is empty, then it means Client has no orders, which satisfy Client’s request
 * criteria. The result array is sorted by "clientOrderCreationTimestamp" field with specified
 * order. In case of error, the value of this field should be not Array, but Object.
 */
@Serializable
data class OrderResult(
    /**
     * Order identifier assigned by CEX.IO Spot Trading.
     */
    val orderId: String,
    /**
     * Order identifier assigned by Client.
     */
    val clientOrderId: String,
    /**
     * Client Comp id.
     */
    val clientId: String,
    /**
     * Represents Client’s account id, which was used for order processing. If this value is null,
     * then it means Client’s main account. Otherwise, it means identifier of Client’s sub-account.
     */
    val accountId: String? = null,
    /**
     * Represents current execution status of this order. Status can be PENDING_NEW, NEW,
     * PARTIALLY_FILLED, FILLED, EXPIRED, REJECTED, PENDING_CANCEL, CANCELLED.
     */
    val status: OrderStatus,
    /**
     * Represents whether this order is in the final state or not.
     */
    val statusIsFinal: Boolean,
    /**
     * Represents first currency in currency pair of this order.
     */
    val currency1: String,
    /**
     * Represents second currency in currency pair of this order.
     */
    val currency2: String,
    /**
     * Represents side of this order.
     */
    val side: String,
    /**
     * Represents order type of this order.
     */
    val orderType: String,
    /**
     * Represents time in force of this order. For details see "Order TimeInForce" section. This
     * value can be null for orders where time in force is not applied, for example, for Market
     * orders.
     */
    val timeInForce: String,
    /**
     * Text, which was provided by Client during order creation. If value is null, then it means
     * Client did not provide such text during order creation.
     */
    val comment: String? = null,
    /**
     * Error code if the order is rejected. If value is null, that means there is no error code.
     */
    val rejectCode: Int? = null,
    /**
     * Human readable error description if the order is rejected. If value is null, that means there
     * is no error description.
     */
    val rejectReason: String? = null,
    /**
     * Represents executed amount in currency1. If this value is null, then it means there is no
     * executed amount (order has no executions).
     */
    val executedAmountCcy1: Double? = null,
    /**
     * Represents executed amount in currency2. If this value is null, then it means there is no
     * executed amount (order has no executions).
     */
    val executedAmountCcy2: Double? = null,
    /**
     * Represents order amount in currency1, which was requested by Client. If this value is null,
     * then it means there is no requested amount in currency1 (order should have then requested
     * amount in currency1).
     */
    val requestedAmountCcy1: Double? = null,
    /**
     * Represents order amount in currency2, which was requested by Client. If this value is null,
     * then it means there is no requested amount in currency2 (order should have then requested
     * amount in currency2).
     */
    val requestedAmountCcy2: Double? = null,
    /**
     * Represents amount in currency1, which was hold from Client's balance by CEX.IO Spot Trading
     * before order execution. If this value is null, then it means that amount in currency1 was not
     * hold from Client's account for this order.
     */
    val initialOnHoldAmountCcy1: Double? = null,
    /**
     * Represents amount in currency2, which was hold from Client's balance by CEX.IO Spot Trading
     * before order execution. If this value is null, then it means that amount in currency2 was not
     * hold from Client's account for this order.
     */
    val initialOnHoldAmountCcy2: Double? = null,
    /**
     * Represents order commission amount, which was charged for this order. If this value is null,
     * then it means there is no commission amount charged for this order.
     */
    val feeAmount: Double? = null,
    /**
     * Represents order commission currency, in which feeAmount is calculated for this order.
     */
    val feeCurrency: String? = null,
    /**
     * Represents order price, which was provided by Client during order creation. If this value is
     * null, then it means there is no requested price for this order. It happens for order where
     * price cannot be requested, for example, Market orders or Stop orders.
     */
    val price: Double? = null,
    /**
     * Represents average order execution price. If this value is null, then it means there is no
     * executed amount (order has no executions).
     */
    val averagePrice: Double? = null,
    /**
     * UTC timestamp in milliseconds. Represents a timestamp provided by Client during creation of
     * the order.
     */
    val clientCreateTimestamp: Long,
    /**
     * UTC timestamp in milliseconds. Represents server timestamp when order was received.
     */
    val serverCreateTimestamp: Long,
    /**
     * UTC timestamp in milliseconds. Represents server timestamp when order changed its state last
     * time.
     */
    val lastUpdateTimestamp: Long,
    /**
     * UTC timestamp in milliseconds. Represents an expired timestamp provided by Client during
     * creation of the order. If this value is null, then it means Client did not provide expire
     * time during order creation.
     */
    val expireTime: Long,
    /**
     * UTC timestamp in milliseconds. Represents an effective timestamp provided by Client during
     * creation of the order. If this value is null, then it means that Client did not provide
     * effective time during order creation.
     */
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
