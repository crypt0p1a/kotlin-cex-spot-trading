package eu.codlab.cex.spot.trading.groups.orders.cancel

import korlibs.time.DateTime
import kotlinx.serialization.Serializable

@Serializable
data class CancelOrder(
    /**
     * Order identifier assigned by CEX.IO Spot Trading. If this field is present and contains valid value, then it means Client wants to cancel specific order with orderId, which was assigned by CEX.IO Spot Trading. If "orderId" field is present, then "clientOrderId" should be absent. Either "orderId" or "clientOrderId" should be indicated in request anyway.
     */
    val orderId: String? = null,
    /**
     * Order identifier assigned by Client when the order was created. If this field is present and contains valid value, then it means Client wants to cancel specific order with clientOrderId indicated by Client at order placement. If "clientOrderId" field is present, then "orderId" field should be absent. Either "clientOrderId" or "orderId" should be indicated in request anyway.
     */
    val clientOrderId: String? = null,
    /**
     * Cancel request identifier assigned by Client.
     */
    val cancelRequestId: String,
    /**
     * Current client time - UTC timestamp in milliseconds.
     */
    val timestamp: Long = DateTime.nowUnixMillisLong()
)