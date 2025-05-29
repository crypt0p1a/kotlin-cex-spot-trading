package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.calls.IRestApi
import eu.codlab.cex.spot.trading.groups.candles.Candle
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPair
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPairs
import eu.codlab.cex.spot.trading.groups.candles.GetCandles
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistory
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequest
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithDate
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithTrade
import eu.codlab.cex.spot.trading.groups.orderbook.OrderBook
import eu.codlab.cex.spot.trading.groups.orderbook.OrderBookRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

sealed class CommonApi {
    abstract val call: IRestApi

    /**
     * This method allows Client to receive current order book snapshot for specific trading pair.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-order-book
     * https://trade.cex.io/docs/#rest-private-api-calls-order-book
     */
    suspend fun orderBook(currency1: String, currency2: String) =
        call.call(
            "/get_order_book",
            OrderBookRequest("$currency1-$currency2"),
            OrderBookRequest.serializer(),
            OrderBook.serializer()
        )

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
    suspend fun candles(
        request: CandlesFromPairs
    ) = candles(
        request.toGetCandles(),
        MapSerializer(
            String.serializer(),
            ListSerializer(
                Candle.serializer()
            )
        )
    )

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
    suspend fun candles(
        request: CandlesFromPair
    ) = candles(
        request.toGetCandles(),
        ListSerializer(
            Candle.serializer()
        )
    )

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
    private suspend fun <O> candles(
        request: GetCandles,
        deserializer: KSerializer<O>
    ) = call.call(
        "get_candles",
        request,
        GetCandles.serializer(),
        deserializer
    )

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
    suspend fun tradeHistory(request: TradeHistoryRequestWithTrade) =
        call.call(
            "get_trade_history",
            request.to(),
            TradeHistoryRequest.serializer(),
            TradeHistory.serializer()
        )

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
    suspend fun tradeHistory(request: TradeHistoryRequestWithDate) =
        call.call(
            "get_trade_history",
            request.to(),
            TradeHistoryRequest.serializer(),
            TradeHistory.serializer()
        )
}
