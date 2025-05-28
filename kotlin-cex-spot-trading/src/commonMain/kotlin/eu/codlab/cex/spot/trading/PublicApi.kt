package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.calls.RestApiPublic
import eu.codlab.cex.spot.trading.models.Currencies
import eu.codlab.cex.spot.trading.models.CurrencyInfo
import eu.codlab.cex.spot.trading.models.OrderBook
import eu.codlab.cex.spot.trading.models.OrderBookRequest
import eu.codlab.cex.spot.trading.models.PairInfo
import eu.codlab.cex.spot.trading.models.Pairs
import eu.codlab.cex.spot.trading.models.ProcessingInfo
import eu.codlab.cex.spot.trading.models.ServerTime
import eu.codlab.cex.spot.trading.models.Ticker
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistory
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequest
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithDate
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithTrade
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

class PublicApi {
    private val call = RestApiPublic()

    suspend fun currencyInfos() = call.call(
        "get_currencies_info",
        ListSerializer(
            CurrencyInfo.serializer()
        )
    )

    suspend fun orderBook(currency1: String, currency2: String) =
        call.call(
            "/get_order_book",
            OrderBookRequest("$currency1-$currency2"),
            OrderBookRequest.serializer(),
            OrderBook.serializer()
        )

    suspend fun tickers(vararg pair: String) = tickers(pair.asList())

    suspend fun tickers(pairs: List<String>) =
        call.call(
            "/get_ticker",
            Pairs(pairs),
            Pairs.serializer(),
            MapSerializer(
                String.serializer(),
                Ticker.serializer()
            )
        )

    suspend fun serverTime() = call.call("/get_server_time", ServerTime.serializer())

    suspend fun pairsInfo(vararg pair: String) = pairsInfo(pair.asList())

    suspend fun pairsInfo(pairs: List<String>) =
        call.call(
            "/get_pairs_info",
            Pairs(pairs),
            Pairs.serializer(),
            ListSerializer(
                PairInfo.serializer()
            )
        )

    suspend fun processingInfo(vararg currencies: String) = processingInfo(currencies.asList())

    suspend fun processingInfo(currencies: List<String>) =
        call.call(
            "/get_processing_info",
            Currencies(currencies),
            Currencies.serializer(),
            MapSerializer(
                String.serializer(),
                ProcessingInfo.serializer()
            )
        )

    suspend fun tradeHistory(request: TradeHistoryRequestWithTrade) =
        call.call(
            "get_trade_history",
            request.to(),
            TradeHistoryRequest.serializer(),
            TradeHistory.serializer()
        )


    suspend fun tradeHistory(request: TradeHistoryRequestWithDate) =
        call.call(
            "get_trade_history",
            request.to(),
            TradeHistoryRequest.serializer(),
            TradeHistory.serializer()
        )
}