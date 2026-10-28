package eu.codlab.cex.spot.trading.rust

import eu.codlab.cex.spot.trading.rust.bindings.CandleResolution
import eu.codlab.cex.spot.trading.rust.bindings.CandlesFromPair
import eu.codlab.cex.spot.trading.rust.bindings.CexException
import eu.codlab.cex.spot.trading.rust.bindings.DataType
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.time.Clock

class TestPublicApi {
    companion object {
        // shared like in an application: CEX.IO limits the calls per client
        private val configuration = ApiConfiguration()
    }

    private val api = PublicApi(apiConfiguration = configuration)

    @Test
    fun testServerTime() = runTest {
        val time = api.serverTime()
        assertTrue(time.timestamp > 0.0)
        assertTrue(time.isoDate.isNotEmpty())
    }

    @Test
    fun testTickerAndOrderBook() = runTest {
        val tickers = api.tickers("BTC-USD")
        assertTrue((tickers.getValue("BTC-USD").last ?: 0.0) > 0.0)

        val book = api.orderBook("BTC", "USD")
        assertEquals("BTC", book.currency1)
        assertTrue(book.bids.isNotEmpty())
    }

    @Test
    fun testCandles() = runTest {
        val candles = api.candles(
            CandlesFromPair(
                pair = "BTC-USD",
                toMs = Clock.System.now().toEpochMilliseconds(),
                limit = 5,
                dataType = DataType.BEST_ASK,
                resolution = CandleResolution.RES1H,
            )
        )
        assertTrue(candles.isNotEmpty())
    }

    @Test
    fun testPairsInfo() = runTest {
        assertEquals(listOf("BTC"), api.pairsInfo("BTC-USD").map { it.base })
        assertTrue(api.pairsInfo().size > 1)
    }

    @Test
    fun testErrorsAreThrown() = runTest {
        // CEX.IO requires fromMs or toMs
        val error = assertFailsWith<CexException.Network> {
            api.candles(
                CandlesFromPair(
                    pair = "BTC-USD",
                    dataType = DataType.BEST_ASK,
                    resolution = CandleResolution.RES1H,
                )
            )
        }
        assertEquals(422U, error.status.toUInt())
    }

    @Test
    fun testTransportErrors() = runTest {
        PublicApi(restOptions(host = "https://localhost:1")).use { offline ->
            assertFailsWith<CexException.Transport> { offline.serverTime() }
        }
    }
}
