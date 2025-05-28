package eu.codlab.cex.spot.trading.groups.orders.news

import eu.codlab.cex.spot.trading.groups.orders.OrderSide
import eu.codlab.cex.spot.trading.groups.orders.OrderType
import eu.codlab.cex.spot.trading.models.OrderStatus
import korlibs.time.DateTime
import kotlinx.serialization.Serializable

@Serializable
data class NewOrder(
    val clientOrderId: String? = null,
    val accountId: String,
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
    /**
     * String representation of the amount in left
     */
    val amountCcy1: String,
    /**
     * String representation of the amount in right
     */
    val amountCcy2: String,
    /**
     * String representation of the price
     */
    val price: String,
    /**
     * UTC timestamp in milliseconds. If Expire Time is in the past, order will be rejected.
     */
    val expireTime: Long? = null,
    /**
     * Stop Price for Stop and StopLimit types of orders.
     */
    val stopPrice: String? = null
)
