package eu.codlab.cex.spot.trading.rust

import eu.codlab.cex.spot.trading.rust.bindings.CurrencyInfo
import eu.codlab.cex.spot.trading.rust.bindings.PairInfo
import eu.codlab.cex.spot.trading.rust.bindings.ProcessingInfo
import eu.codlab.cex.spot.trading.rust.bindings.ServerTime
import eu.codlab.cex.spot.trading.rust.bindings.Ticker

interface IPublicApi : ICommonApi {
    /**
     * Current prices, 24h price & volume changes, last trade of the pairs, keyed by pair
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-ticker
     */
    suspend fun tickers(vararg pair: String): Map<String, Ticker> = tickers(pair.asList())

    /**
     * Current prices, 24h price & volume changes, last trade of the pairs, keyed by pair
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-ticker
     */
    suspend fun tickers(pairs: List<String>): Map<String, Ticker>

    /**
     * Current time of the Spot Trading server
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-server-time
     */
    suspend fun serverTime(): ServerTime

    /**
     * Parameters of the trading pairs, all of them when none is given
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-pairs-info
     */
    suspend fun pairsInfo(vararg pair: String): List<PairInfo> = pairsInfo(pair.asList())

    /**
     * Parameters of the trading pairs, all of them when the list is empty
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-pairs-info
     */
    suspend fun pairsInfo(pairs: List<String>): List<PairInfo>

    /**
     * Parameters of the currencies and their deposit/withdrawal availability with CEX.IO Wallet
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-currencies-info
     */
    suspend fun currencyInfos(): List<CurrencyInfo>

    /**
     * Deposit and withdrawal options of the cryptocurrencies with external wallets, per blockchain
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-processing-info
     */
    suspend fun processingInfo(vararg currencies: String): Map<String, ProcessingInfo> =
        processingInfo(currencies.asList())

    /**
     * Deposit and withdrawal options of the cryptocurrencies with external wallets, per blockchain
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-processing-info
     */
    suspend fun processingInfo(currencies: List<String>): Map<String, ProcessingInfo>
}
