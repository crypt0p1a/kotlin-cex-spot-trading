package eu.codlab.cex.spot.trading

import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestPublicApi {
    val api = PublicApi()

    @Test
    fun testCurrencyLimits() = runTest {
        println(api.currencyLimits())
    }

    @Test
    fun testTicker() = runTest {
        println(api.ticker("BTC", "USD"))
    }

    @Test
    fun testTickers() = runTest {
        println(api.allTickers("BTC", "USD", "ETH", "EUR", "XRP"))
    }

    @Test
    fun lastPrice() = runTest {
        println(api.lastPrice("ETH", "USD"))
        println(
            api.lastPrices(
                "ETH" to "USD",
                "ETH" to "EUR",
                "BTC" to "EUR"
            )
        )
    }

    @Test
    fun orderBook() = runTest {
        println(api.orderBook("ETH", "EUR"))
    }

    @Test
    fun tradeHistory() = runTest {
        println(api.tradeHistory("BTC", "USD"))
    }
}