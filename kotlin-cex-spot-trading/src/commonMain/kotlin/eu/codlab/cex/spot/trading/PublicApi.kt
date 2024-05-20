package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.calls.RestApiPublic
import eu.codlab.cex.spot.trading.models.CurrencyLimitPairs
import eu.codlab.cex.spot.trading.models.LastPrice
import eu.codlab.cex.spot.trading.models.LastPriceCorrect
import eu.codlab.cex.spot.trading.models.OrderBook
import eu.codlab.cex.spot.trading.models.Ticker
import eu.codlab.cex.spot.trading.models.TradeHistory
import kotlinx.serialization.builtins.ListSerializer

class PublicApi {
    private val call = RestApiPublic()

    suspend fun currencyLimits() = call.call("currency_limits", CurrencyLimitPairs.serializer())

    suspend fun ticker(symbol1: String, symbol2: String) =
        call.call("ticker/$symbol1/$symbol2", Ticker.serializer())

    suspend fun allTickers(vararg symbols: String) =
        call.call(
            "tickers/${symbols.joinToString("/")}",
            ListSerializer(
                Ticker.serializer()
            )
        )

    suspend fun lastPrice(symbol1: String, symbol2: String) =
        call.call("last_price/$symbol1/$symbol2", LastPrice.serializer())

    suspend fun lastPrices(vararg pairs: Pair<String, String>): List<LastPrice> {
        val mapped = pairs.joinToString("/") { "${it.first}/${it.second}" }
        return call.call(
            "last_prices/$mapped", ListSerializer(
                LastPriceCorrect.serializer()
            )
        )?.map { it.to() } ?: emptyList()
    }

    //suspend fun historical1m(date: Long, symbol1: String, symbol2: String) =
    //    call.call("ohlcv/hd/$date/$symbol1/$symbol2")

    suspend fun orderBook(symbol1: String, symbol2: String, limit: Int = 1) =
        call.call(
            if (limit > 0) {
                "order_book/$symbol1/$symbol2?depth=$limit"
            } else {
                "order_book/$symbol1/$symbol2"
            },
            OrderBook.serializer()
        )

    suspend fun tradeHistory(symbol1: String, symbol2: String, sinceTid: Int = 0) =
        call.call(
            if (sinceTid > 0) {
                "trade_history/$symbol1/$symbol2?since=$sinceTid"
            } else {
                "trade_history/$symbol1/$symbol2"
            },
            ListSerializer(
                TradeHistory.serializer()
            )
        )
}