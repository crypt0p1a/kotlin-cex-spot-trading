package eu.codlab.cex.spot.trading.rust

import eu.codlab.cex.spot.trading.rust.bindings.CexPrivateApi
import eu.codlab.cex.spot.trading.rust.bindings.AccountStatusRequest
import eu.codlab.cex.spot.trading.rust.bindings.CancelOrder
import eu.codlab.cex.spot.trading.rust.bindings.CandlesFromPair
import eu.codlab.cex.spot.trading.rust.bindings.CandlesFromPairs
import eu.codlab.cex.spot.trading.rust.bindings.DepositToOrWithdrawalFromWalletRequest
import eu.codlab.cex.spot.trading.rust.bindings.NewOrder
import eu.codlab.cex.spot.trading.rust.bindings.OrderRequest
import eu.codlab.cex.spot.trading.rust.bindings.RestOptions
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistoryRequestWithDate
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistoryRequestWithTrade
import eu.codlab.cex.spot.trading.rust.bindings.TransactionHistoryRequest

/**
 * Private API of CEX.IO Spot Trading, implemented in Rust, signed with the API key and secret
 */
@Suppress("TooManyFunctions")
class PrivateApi(
    apiKey: String,
    apiSecret: String,
    restOptions: RestOptions = restOptions(),
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
) : IPrivateApi {
    private val api = CexPrivateApi(
        apiKey,
        apiSecret,
        restOptions,
        apiConfiguration.rateLimitQueue.native
    )

    override suspend fun orderBook(currency1: String, currency2: String) =
        api.orderBook(currency1, currency2)

    override suspend fun candles(request: CandlesFromPairs) = api.candlesFromPairs(request)

    override suspend fun candles(request: CandlesFromPair) = api.candlesFromPair(request)

    override suspend fun tradeHistory(request: TradeHistoryRequestWithTrade) =
        api.tradeHistoryWithTrade(request)

    override suspend fun tradeHistory(request: TradeHistoryRequestWithDate) =
        api.tradeHistoryWithDate(request)

    override suspend fun getMyCurrentFee(pairs: List<String>) = api.getMyCurrentFee(pairs)

    override suspend fun getFeeStrategy() = api.getFeeStrategy()

    override suspend fun getMyVolume() = api.getMyVolume()

    override suspend fun createAccount(accountId: String, currency: String) =
        api.createAccount(accountId, currency)

    override suspend fun getMyAccountStatus(request: AccountStatusRequest) =
        api.getMyAccountStatus(request)

    override suspend fun accountBalance() = api.accountBalance()

    override suspend fun orders(request: OrderRequest) = api.orders(request)

    override suspend fun orders(orderId: String) = api.order(orderId)

    override suspend fun orders(symbols: Pair<String, String>) =
        api.ordersForPair(symbols.first, symbols.second)

    override suspend fun newOrder(request: NewOrder) = api.newOrder(request)

    override suspend fun cancelOrder(request: CancelOrder) = api.cancelOrder(request)

    override suspend fun cancelOrders() = api.cancelOrders()

    override suspend fun transactionHistory(request: TransactionHistoryRequest) =
        api.transactionHistory(request)

    override suspend fun fundsDepositFromWallet(request: DepositToOrWithdrawalFromWalletRequest) =
        api.fundsDepositFromWallet(request)

    override suspend fun fundsWithdrawToWallet(request: DepositToOrWithdrawalFromWalletRequest) =
        api.fundsWithdrawToWallet(request)

    override fun close() = api.close()
}
