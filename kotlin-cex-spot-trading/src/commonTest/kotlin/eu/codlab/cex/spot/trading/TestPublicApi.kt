package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.groups.candles.CandleResolution
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPair
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPairs
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithTrade
import eu.codlab.cex.spot.trading.models.DataType
import korlibs.time.DateTime
import korlibs.time.hours
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestPublicApi {
    @OptIn(DelicateCoroutinesApi::class)
    val api = PublicApi(GlobalScope)

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
