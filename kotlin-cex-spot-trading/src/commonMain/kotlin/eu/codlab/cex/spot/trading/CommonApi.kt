package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.bridge.rustCall
import eu.codlab.cex.spot.trading.bridge.toKotlin
import eu.codlab.cex.spot.trading.bridge.toRust
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPair
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPairs
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithDate
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithTrade
import eu.codlab.cex.spot.trading.rust.ICommonApi as RustCommonApi

sealed class CommonApi : ICommonApi {
    internal abstract val rust: RustCommonApi

    /**
     * This method allows Client to receive current order book snapshot for specific trading pair.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-order-book
     * https://trade.cex.io/docs/#rest-private-api-calls-order-book
     */
    override suspend fun orderBook(currency1: String, currency2: String) =
        rustCall { rust.orderBook(currency1, currency2).toKotlin() }

    /**
     * By using Candles method Client can receive historical OHLCV candles of different resolutions
     * and data types.
     *
     * Client can indicate additional timeframe and limit filters to make response more precise to
     * Client’s requirements.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-candles
     * https://trade.cex.io/docs/#rest-private-api-calls-candles
     */
    override suspend fun candles(request: CandlesFromPairs) = rustCall {
        rust.candles(request.toRust()).mapValues { candles -> candles.value.map { it.toKotlin() } }
    }

    /**
     * By using Candles method Client can receive historical OHLCV candles of different resolutions
     * and data types.
     *
     * Client can indicate additional timeframe and limit filters to make response more precise to
     * Client’s requirements.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-candles
     * https://trade.cex.io/docs/#rest-private-api-calls-candles
     */
    override suspend fun candles(request: CandlesFromPair) = rustCall {
        rust.candles(request.toRust()).map { it.toKotlin() }
    }

    /**
     * This method allows Client to obtain historical data as to occurred trades upon requested
     * trading pair.
     *
     * Client can supplement Trade History request with additional filter parameters, such as
     * timeframe period, tradeIds range, side etc. to receive trades which match request parameters.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-trade-history
     * https://trade.cex.io/docs/#rest-private-api-calls-trade-history
     */
    override suspend fun tradeHistory(request: TradeHistoryRequestWithTrade) =
        rustCall { rust.tradeHistory(request.toRust()).toKotlin() }

    /**
     * This method allows Client to obtain historical data as to occurred trades upon requested
     * trading pair.
     *
     * Client can supplement Trade History request with additional filter parameters, such as
     * timeframe period, tradeIds range, side etc. to receive trades which match request parameters.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-trade-history
     * https://trade.cex.io/docs/#rest-private-api-calls-trade-history
     */
    override suspend fun tradeHistory(request: TradeHistoryRequestWithDate) =
        rustCall { rust.tradeHistory(request.toRust()).toKotlin() }

    override fun shutdown() = rust.close()
}
