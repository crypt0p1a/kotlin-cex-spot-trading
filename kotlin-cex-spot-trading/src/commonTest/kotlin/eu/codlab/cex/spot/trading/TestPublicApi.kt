package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithTrade
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestPublicApi {
    val api = PublicApi()

    @Test
    fun testCurrencyInfos() = runTest {
        println(api.currencyInfos())
    }

    @Test
    fun testTicker() = runTest {
        println(api.tickers("BTC-USD"))
    }

    @Test
    fun testPairsInfo() = runTest {
        println(api.pairsInfo("BTC-USD"))
        println(api.pairsInfo("BTC-USD", "ETH-EUR"))
    }

    @Test
    fun testProcessingInfo() = runTest {
        println(api.processingInfo("BTC"))
        println(api.processingInfo("ETH", "BTC"))
    }

    @Test
    fun orderBook() = runTest {
        println(api.orderBook("ETH", "EUR"))
    }

    @Test
    fun candles() = runTest {
        /*println(
            api.candles(
                CandleSinglePair(
                    "ETH-EUR",
                    fromISOMs = 1676041279412,
                    limit = 10
                )
            )
        )

        println(
            api.candles(
                CandlePairs(
                    listOf("ETH-EUR", "ETH-USD"),
                    fromISOMs = 1676041279412
                )
            )
        )*/
    }

    @Test
    fun tradeHistory() = runTest {
        println(
            api.tradeHistory(
                TradeHistoryRequestWithTrade(
                    pair = "ETH-USD"
                )
            )
        )
    }
}