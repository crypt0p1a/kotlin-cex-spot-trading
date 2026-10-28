package eu.codlab.cex.spot.trading.rust

import eu.codlab.cex.spot.trading.rust.bindings.CexPublicApi
import eu.codlab.cex.spot.trading.rust.bindings.CandlesFromPair
import eu.codlab.cex.spot.trading.rust.bindings.CandlesFromPairs
import eu.codlab.cex.spot.trading.rust.bindings.RestOptions
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistoryRequestWithDate
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistoryRequestWithTrade

/**
 * Public API of CEX.IO Spot Trading, implemented in Rust
 */
class PublicApi(
    restOptions: RestOptions = restOptions(),
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
) : IPublicApi {
    private val api = CexPublicApi(restOptions, apiConfiguration.rateLimitQueue.native)

    override suspend fun orderBook(currency1: String, currency2: String) =
        api.orderBook(currency1, currency2)

    override suspend fun candles(request: CandlesFromPairs) = api.candlesFromPairs(request)

    override suspend fun candles(request: CandlesFromPair) = api.candlesFromPair(request)

    override suspend fun tradeHistory(request: TradeHistoryRequestWithTrade) =
        api.tradeHistoryWithTrade(request)

    override suspend fun tradeHistory(request: TradeHistoryRequestWithDate) =
        api.tradeHistoryWithDate(request)

    override suspend fun tickers(pairs: List<String>) = api.tickers(pairs)

    override suspend fun serverTime() = api.serverTime()

    override suspend fun pairsInfo(pairs: List<String>) = api.pairsInfo(pairs)

    override suspend fun currencyInfos() = api.currencyInfos()

    override suspend fun processingInfo(currencies: List<String>) = api.processingInfo(currencies)

    override fun close() = api.close()
}
