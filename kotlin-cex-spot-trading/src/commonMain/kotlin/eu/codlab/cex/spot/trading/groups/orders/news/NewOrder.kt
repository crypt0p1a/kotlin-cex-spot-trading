package eu.codlab.cex.spot.trading.groups.orders.news

import eu.codlab.cex.spot.trading.groups.orders.OrderSide
import eu.codlab.cex.spot.trading.groups.orders.OrderType
import korlibs.time.DateTime
import kotlinx.serialization.Serializable

@Serializable
data class NewOrder(
    /**
     * Order identifier assigned by Client. If this field is absent, it will be set automatically to current timestamp in milliseconds.
     */
    val clientOrderId: String? = null,
    /**
     * Client’s sub-account ID. Empty string value ("") is not allowed in this field.
     */
    val accountId: String,
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
    val side: OrderSide,
    /**
     * Represents order type of this order.
     */
    val orderType: OrderType,
    /**
     * UTC timestamp in milliseconds, represents client-side order creation time. By default,
     * timestamp should be within 30000 ms timeframe with server time, otherwise, order will be
     * rejected. Please be informed that default timeframe value 30000 ms can be changed for the
     * Client by request.
     */
    val timestamp: Long = DateTime.nowUnixMillisLong(),
    /**
     * Comment for order. Maximum length of comment string is 255 characters. If value is null,
     * then it means Client did not provide such text during order creation.
     */
    val comment: String,
    /**
     * Represents order amount in currency1. This value can be null if order requests amount in
     * currency2.
     */
    val amountCcy1: String?,
    /**
     * Represents order amount in currency2. This value can be null if order requests amount in
     * currency1.
     */
    val amountCcy2: String?,
    /**
     * Represents order price. Please omit this field for orders, where price cannot be requested,
     * for example, Market orders or Stop orders.
     */
    val price: String,
    /**
     * UTC timestamp in format YYYYMMDD-HH:mm:ss.SSS. If Expire Time is in the past, order will be rejected.
     */
    val expireTime: String? = null,
    /**
     * Stop Price for Stop and StopLimit types of orders.
     */
    val stopPrice: String? = null,
    val timeInForce: TimeInForce,
)
