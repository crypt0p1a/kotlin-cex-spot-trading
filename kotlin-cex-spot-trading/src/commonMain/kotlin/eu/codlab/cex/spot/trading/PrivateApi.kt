package eu.codlab.cex.spot.trading

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
import eu.codlab.cex.spot.trading.groups.account.PrivateAccountApi

class PrivateApi(
    clientId: String,
    apiKey: String,
    apiSecret: String
) {
    val account = PrivateAccountApi(clientId, apiKey, apiSecret)

    private val call = RestApiSecret(
        clientId,
        apiKey,
        apiSecret
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-current-fee
     */
    suspend fun getMyCurrentFee(pairs: Pairs) = account.getMyCurrentFee(pairs)

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-fee-strategy
     */
    suspend fun getFeeStrategy() = account.getFeeStrategy()

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-volume
     */
    suspend fun getMyVolume() = call.call(
        "get_my_volume",
        Volume.serializer()
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-create-account
     */
    suspend fun createAccount(accountId: String, currency: String) =
        account.createAccount(accountId, currency)

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-account-status-v3
     */
    suspend fun getMyAccountStatus(request: AccountStatusRequest) =
        account.getMyAccountStatus(request)

    suspend fun getAllOpenOrders(request: OrderRequest) = call.call(
        "get_my_orders",
        request.to(),
        OrderRequestInternal.serializer(),
        ListSerializer(OrderResult.serializer())
    )


    suspend fun getAllOpenOrders(orderId: String) = call.call(
        "get_my_orders",
        OrderRequestInternal(clientOrderId = orderId),
        OrderRequestInternal.serializer(),
        ListSerializer(OrderResult.serializer())
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-wallet-balance
     */
    suspend fun accountBalance() = account.accountBalance()

    suspend fun orders(symbols: Pair<String, String>? = null): List<OpenOrder> {
        return call.call(
            "get_my_orders",
            symbols?.let { OrdersParam("${symbols.first}-${symbols.second}") },
            OrdersParam.serializer(),
            ListSerializer(OpenOrder.serializer())
        ) ?: emptyList()
    }

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-new-order
     */
    suspend fun newOrder(
        request: NewOrder
    ) = call.call(
        "do_my_new_order",
        request,
        NewOrder.serializer(),
        NewOrderAnswer.serializer()
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-cancel-order
     *
     * Will throw in case of errors
     */
    suspend fun cancelOrder(
        request: CancelOrder
    ) {
        call.call(
            "do_cancel_my_order",
            request,
            CancelOrder.serializer(),
            EmptyBody.serializer()
        )
    }

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-cancel-all-orders
     */
    suspend fun cancelOrders() = call.call(
        "do_cancel_all_orders",
        CancelOrders.serializer()
    )?.clientOrderIds

    /**
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
     * https://trade.cex.io/docs/#rest-private-api-calls-trade-history
     */
    suspend fun tradeHistory(request: TradeHistoryRequestWithTrade) = call.call(
        "get_trade_history",
        request.to(),
        TradeHistoryRequest.serializer(),
        TradeHistory.serializer()
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-trade-history
     */
    suspend fun tradeHistory(request: TradeHistoryRequestWithDate) = call.call(
        "get_trade_history",
        request.to(),
        TradeHistoryRequest.serializer(),
        TradeHistory.serializer()
    )

    /**
     * https://trade.cex.io/docs/#rest-private-api-calls-transaction-history
     */
    suspend fun transactionHistory(request: TransactionHistoryRequest) = call.call(
        "get_my_transaction_history",
        request,
        TransactionHistoryRequest.serializer(),
        ListSerializer(TransactionHistory.serializer())
    )
}