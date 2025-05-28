package eu.codlab.cex.spot.trading.groups.account

import eu.codlab.cex.spot.trading.calls.RestApiSecret
import eu.codlab.cex.spot.trading.groups.account.balance.AccountBalance
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusRequest
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusResult
import eu.codlab.cex.spot.trading.groups.account.create.CreateAccountRequest
import eu.codlab.cex.spot.trading.groups.account.create.CreateAccountResult
import eu.codlab.cex.spot.trading.groups.candles.Candle
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPair
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPairs
import eu.codlab.cex.spot.trading.groups.candles.GetCandles
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistory
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequest
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithDate
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithTrade
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionHistory
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionHistoryRequest
import eu.codlab.cex.spot.trading.groups.orders.cancel.CancelOrder
import eu.codlab.cex.spot.trading.groups.orders.cancel.CancelOrders
import eu.codlab.cex.spot.trading.groups.orders.EmptyBody
import eu.codlab.cex.spot.trading.groups.orders.news.NewOrder
import eu.codlab.cex.spot.trading.groups.orders.news.NewOrderAnswer
import eu.codlab.cex.spot.trading.groups.orders.open.OpenOrder
import eu.codlab.cex.spot.trading.groups.orders.OrdersParam
import eu.codlab.cex.spot.trading.groups.fees.FeeStrategy
import eu.codlab.cex.spot.trading.models.OrderRequest
import eu.codlab.cex.spot.trading.models.OrderRequestInternal
import eu.codlab.cex.spot.trading.models.OrderResult
import eu.codlab.cex.spot.trading.models.Pairs
import eu.codlab.cex.spot.trading.models.TradingFees
import eu.codlab.cex.spot.trading.groups.volume.Volume
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

class PrivateAccountApi internal constructor(
    clientId: String,
    apiKey: String,
    apiSecret: String
) {
    private val call = RestApiSecret(
        clientId,
        apiKey,
        apiSecret
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-create-account
     */
    suspend fun createAccount(accountId: String, currency: String) = call.call(
        "do_create_account",
        CreateAccountRequest(
            accountId = accountId,
            currency = currency
        ),
        CreateAccountRequest.serializer(),
        CreateAccountResult.serializer()
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-account-status-v3
     */
    suspend fun getMyAccountStatus(request: AccountStatusRequest) = call.call(
        "get_my_account_status_v3",
        request,
        AccountStatusRequest.serializer(),
        AccountStatusResult.serializer()
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-wallet-balance
     */
    suspend fun accountBalance() = call.call(
        "get_my_wallet_balance", MapSerializer(
            String.serializer(),
            AccountBalance.serializer()
        )
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-current-fee
     */
    suspend fun getMyCurrentFee(pairs: Pairs) = call.call(
        "get_my_current_fee",
        pairs,
        Pairs.serializer(),
        TradingFees.serializer()
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-fee-strategy
     */
    suspend fun getFeeStrategy() = call.call(
        "get_fee_strategy",
        FeeStrategy.serializer()
    )
}