package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.groups.currency.CurrencyInfo
import eu.codlab.cex.spot.trading.groups.pairsinfo.PairInfo
import eu.codlab.cex.spot.trading.groups.processing.ProcessingInfo
import eu.codlab.cex.spot.trading.groups.ticker.Ticker
import eu.codlab.cex.spot.trading.models.ServerTime

interface IPublicApi : ICommonApi {
    /**
     * This method is designed to obtain current information about Ticker, including data about
     * current prices, 24h price & volume changes, last trade event etc. of certain assets.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-trade-history
     */
    suspend fun tickers(vararg pair: String): Map<String, Ticker>

    /**
     * This method is designed to obtain current information about Ticker, including data about
     * current prices, 24h price & volume changes, last trade event etc. of certain assets.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-trade-history
     */
    suspend fun tickers(pairs: List<String>): Map<String, Ticker>

    /**
     * This method is used to get the current time on Spot Trading server. It can be useful for
     * applications that have to be synchronized with the server's time.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-server-time
     */
    suspend fun serverTime(): ServerTime

    /**
     * Pair Info method allows Client to receive the parameters for all supported trading pairs.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-pairs-info
     */
    suspend fun pairsInfo(vararg pair: String): List<PairInfo>

    /**
     * Pair Info method allows Client to receive the parameters for all supported trading pairs.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-pairs-info
     */
    suspend fun pairsInfo(pairs: List<String>): List<PairInfo>

    /**
     * Currencies Info method allows Client to receive the parameters for all currencies configured
     * in CEX.IO Spot Trading as well as the deposit and withdrawal availability between CEX.IO Spot
     * Trading and CEX.IO Wallet.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-currencies-info
     */
    suspend fun currencyInfos(): List<CurrencyInfo>

    /**
     * This request allows Client to receive detailed information about available options to make
     * deposits from external wallets and withdrawals to external wallets as to each supported
     * cryptocurrency, including cryptocurrency name and available blockchains for
     * deposit/withdrawals. Also, as to each supported blockchain there are indicated type of
     * cryptocurrency on indicated blockchain, current deposit\withdrawal availability, minimum
     * amounts for deposits\withdrawals, external withdrawal fees.
     *
     * Processing Information makes Client more flexible in choosing desired blockchain for
     * receiving Deposit address and initiating external withdrawals via certain blockchain, so
     * that Client uses more convenient way of transferring his crypto assets to or from CEX.IO
     * Ecosystem.
     *
     * Note that this method indicates minimum deposit\withdrawal limits and external withdrawal
     * fees for external crypto transfers. Currently, deposits and withdrawals of funds between
     * CEX.IO Wallet and CEX.IO Spot Trading account are free.
     * Currently, external withdrawals are not supported via CEX.IO Spot Trading API.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-processing-info
     */
    suspend fun processingInfo(vararg currencies: String): Map<String, ProcessingInfo>

    /**
     * This request allows Client to receive detailed information about available options to make
     * deposits from external wallets and withdrawals to external wallets as to each supported
     * cryptocurrency, including cryptocurrency name and available blockchains for
     * deposit/withdrawals. Also, as to each supported blockchain there are indicated type of
     * cryptocurrency on indicated blockchain, current deposit\withdrawal availability, minimum
     * amounts for deposits\withdrawals, external withdrawal fees.
     *
     * Processing Information makes Client more flexible in choosing desired blockchain for
     * receiving Deposit address and initiating external withdrawals via certain blockchain, so
     * that Client uses more convenient way of transferring his crypto assets to or from CEX.IO
     * Ecosystem.
     *
     * Note that this method indicates minimum deposit\withdrawal limits and external withdrawal
     * fees for external crypto transfers. Currently, deposits and withdrawals of funds between
     * CEX.IO Wallet and CEX.IO Spot Trading account are free.
     * Currently, external withdrawals are not supported via CEX.IO Spot Trading API.
     *
     * https://trade.cex.io/docs/#rest-public-api-calls-processing-info
     */
    suspend fun processingInfo(currencies: List<String>): Map<String, ProcessingInfo>
}
