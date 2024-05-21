package eu.codlab.cex.spot.trading

import eu.codlab.configuration.Configuration
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestPrivateApi {
    val api = PrivateApi(
        Configuration.clientId,
        Configuration.apiKey,
        Configuration.apiSecret
    )

    @Test
    fun testBalance() = runTest {
        println(api.accountBalance())
    }

    @Test
    fun testOpenOrders() = runTest {
        println(api.openOrders("ETH" to "EUR"))
    }

    @Test
    fun testCancelOrder() = runTest {
        val transactionId = api.openOrders().firstOrNull()?.id
            ?: return@runTest
        // assuming that a transaction exists
        println("cancelling $transactionId")
        println(api.cancelOrder(transactionId))
    }

    @Test
    fun testCancelOrders() = runTest {
        val transaction = api.openOrders().firstOrNull()
            ?: return@runTest
        // assuming that a transaction exists
        println("cancelling $transaction")
        println(api.cancelPairOrders(transaction.symbol1 to transaction.symbol2))
    }

    @Test
    fun testGetOrderDetails() = runTest {
        val order = api.openOrders().first()
        // assuming that a transaction exists
        println("cancelling $order")
        println(api.getOrderDetails(order.id))
    }

    @Test
    fun testGetOrderTx() = runTest {
        val order = api.openOrders().first()
        // assuming that a transaction exists
        println("cancelling $order")
        println(api.getOrderTx(order.id))
    }
}
