package eu.codlab.cex

import eu.codlab.cex.spot.trading.PrivateApi
import eu.codlab.cex.spot.trading.groups.orders.cancel.CancelOrder
import korlibs.time.DateTime
import kotlinx.coroutines.runBlocking

fun PrivateApi.cancelOrder(orderId: String?) = runBlocking {
    cancelOrder(
        CancelOrder(
            orderId = orderId!!,
            cancelRequestId = "${DateTime.nowUnixMillisLong()}"
        )
    )

    println(orders())
}
