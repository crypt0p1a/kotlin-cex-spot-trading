package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.calls.RestApiSecret
import eu.codlab.cex.spot.trading.groups.account.PrivateAccountApi
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusRequest
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionHistory
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionHistoryRequest
import eu.codlab.cex.spot.trading.groups.orders.EmptyBody
import eu.codlab.cex.spot.trading.groups.orders.OrdersParam
import eu.codlab.cex.spot.trading.groups.orders.cancel.CancelOrder
import eu.codlab.cex.spot.trading.groups.orders.cancel.CancelOrders
import eu.codlab.cex.spot.trading.groups.orders.news.NewOrder
import eu.codlab.cex.spot.trading.groups.orders.news.NewOrderAnswer
import eu.codlab.cex.spot.trading.groups.orders.open.OpenOrder
import eu.codlab.cex.spot.trading.groups.pairsinfo.Pairs
import eu.codlab.cex.spot.trading.groups.volume.Volume
import eu.codlab.cex.spot.trading.models.OrderRequest
import eu.codlab.cex.spot.trading.models.OrderRequestInternal
import eu.codlab.cex.spot.trading.models.OrderResult
import kotlinx.serialization.builtins.ListSerializer

class PrivateApi(
    clientId: String,
    apiKey: String,
    apiSecret: String
) : CommonApi() {
    val account = PrivateAccountApi(clientId, apiKey, apiSecret)

    override val call = RestApiSecret(
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
     * https://trade.cex.io/docs/#rest-private-api-calls-transaction-history
     */
    suspend fun transactionHistory(request: TransactionHistoryRequest) = call.call(
        "get_my_transaction_history",
        request,
        TransactionHistoryRequest.serializer(),
        ListSerializer(TransactionHistory.serializer())
    )
}
