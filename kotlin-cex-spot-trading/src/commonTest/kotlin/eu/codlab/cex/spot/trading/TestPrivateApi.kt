package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusRequest
import eu.codlab.cex.spot.trading.groups.candles.CandleResolution
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPair
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPairs
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithTrade
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionHistoryRequest
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionType
import eu.codlab.cex.spot.trading.groups.orders.OrderSide
import eu.codlab.cex.spot.trading.groups.orders.cancel.CancelOrder
import eu.codlab.cex.spot.trading.groups.pairsinfo.Pairs
import eu.codlab.cex.spot.trading.models.DataType
import eu.codlab.cex.spot.trading.rest.RestOptions
import eu.codlab.configuration.Configuration
import korlibs.time.DateTime
import korlibs.time.hours
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestPrivateApi {
    val api = PrivateApi(
        Configuration.apiKey,
        Configuration.apiSecret,
        RestOptions(enableLogs = true)
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-current-fee
     */
    @Test
    fun testGetMyCurrentFee() = runTest {
        println(api.getMyCurrentFee(Pairs(listOf())))
    }

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-fee-strategy
     */
    @Test
    fun testFeeStrategy() = runTest {
        println(api.getFeeStrategy())
    }

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-volume
     */
    @Test
    fun testGetMyVolume() = runTest {
        println(api.getMyVolume())
    }

    @Test
    fun testCreateAccount() = runTest {
        println(
            api.createAccount(
                accountId = "test1",
                currency = "USD"
            )
        )
    }

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-account-status-v3
     */
    @Test
    fun testGetMyAccountStatus() = runTest {
        println(api.getMyAccountStatus(AccountStatusRequest()))
    }

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-wallet-balance
     */
    @Test
    fun testBalance() = runTest {
        println(api.accountBalance())
    }

    @Test
    fun testOpenOrders() = runTest {
        println(api.orders("ETH" to "EUR"))
        println(api.orders())
    }

    @Test
    fun testCancelOrder() = runTest {
        val order = api.orders().firstOrNull()
            ?: return@runTest
        // assuming that a transaction exists
        println("cancelling $order")
        println(
            api.cancelOrder(
                CancelOrder(
                    orderId = order.orderId,
                    cancelRequestId = DateTime.nowUnixMillisLong().toString()
                )
            )
        )
    }

    @Test
    fun testCancelOrders() = runTest {
        val transaction = api.orders().firstOrNull()
            ?: return@runTest
        // assuming that a transaction exists
        println("cancelling $transaction")
        println(api.cancelOrders())
    }

    @Test
    fun testGetCandles() = runTest {
        val candles = api.candles(
            CandlesFromPair(
                "BTC-USD",
                dataType = DataType.BestBid,
                resolution = CandleResolution.Res1h,
                limit = null,
                toISO = DateTime.now(),
                fromISO = DateTime.now().minus(5.hours)
            )
        )
        // assuming that a transaction exists
        println("candles $candles")

        val others = api.candles(
            CandlesFromPairs(
                pairsList = listOf("BTC-USD"),
                dataType = DataType.BestBid,
                resolution = CandleResolution.Res1h,
                toISO = null,
                // toISO = DateTime.now(),
                fromISO = DateTime.now().minus(5.hours)
            )
        )
        // assuming that a transaction exists
        println("others $others")
    }

    @Test
    fun testTradeHistory() = runTest {
        val history = api.tradeHistory(
            TradeHistoryRequestWithTrade(
                pair = "BTC-USD",
                side = OrderSide.Sell,
            )
        )

        println("history $history")
    }

    @Test
    fun testTransactionHistory() = runTest {
        val history = api.transactionHistory(
            TransactionHistoryRequest(
                accountId = "",
                type = TransactionType.Trade,
            )
        )

        println("history, $history")
    }

    /*@Test
    fun testGetOrderTx() = runTest {
        val order = api.orders().first()
        // assuming that a transaction exists
        println("cancelling $order")
        println(api.getOrderTx(order.id))
    }*/
}
