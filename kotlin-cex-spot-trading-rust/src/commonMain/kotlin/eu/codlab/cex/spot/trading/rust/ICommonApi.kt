package eu.codlab.cex.spot.trading.rust

import eu.codlab.cex.spot.trading.rust.bindings.Candle
import eu.codlab.cex.spot.trading.rust.bindings.CandlesFromPair
import eu.codlab.cex.spot.trading.rust.bindings.CandlesFromPairs
import eu.codlab.cex.spot.trading.rust.bindings.OrderBook
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistory
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistoryRequestWithDate
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistoryRequestWithTrade

/**
 * The calls throw [eu.codlab.cex.spot.trading.rust.bindings.CexException] on failure
 */
interface ICommonApi : AutoCloseable {
    /**
     * Current order book snapshot of a trading pair
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-order-book
     * https://trade.cex.io/docs/#rest-private-api-calls-order-book
     */
    suspend fun orderBook(currency1: String, currency2: String): OrderBook

    /**
     * Last historical OHLCV candle of several pairs, keyed by pair
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-candles
     * https://trade.cex.io/docs/#rest-private-api-calls-candles
     */
    suspend fun candles(request: CandlesFromPairs): Map<String, List<Candle>>

    /**
     * Historical OHLCV candles of a pair, CEX.IO requires either fromMs or toMs
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-candles
     * https://trade.cex.io/docs/#rest-private-api-calls-candles
     */
    suspend fun candles(request: CandlesFromPair): List<Candle>

    /**
     * Trades of a pair, filtered by trade ids
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-trade-history
     * https://trade.cex.io/docs/#rest-private-api-calls-trade-history
     */
    suspend fun tradeHistory(request: TradeHistoryRequestWithTrade): TradeHistory

    /**
     * Trades of a pair, filtered by dates
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-trade-history
     * https://trade.cex.io/docs/#rest-private-api-calls-trade-history
     */
    suspend fun tradeHistory(request: TradeHistoryRequestWithDate): TradeHistory

    /**
     * Releases the native API, the instance can't be used afterward
     */
    fun shutdown() = close()
}
