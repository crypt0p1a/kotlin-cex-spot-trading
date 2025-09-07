package eu.codlab.cex

import eu.codlab.cex.spot.trading.PrivateApi
import eu.codlab.cex.spot.trading.models.OrderRequest
import kotlinx.coroutines.runBlocking

fun PrivateApi.showOrders(walletName: String?, orderId: String?) = runBlocking {
    val orders = orders(
        OrderRequest(
            orderId = orderId,
        ).let {
            if (null != walletName) {
                it.copy(accountIds = listOf(walletName))
            } else {
                it
            }
        }
    )

    println(orders)
}
