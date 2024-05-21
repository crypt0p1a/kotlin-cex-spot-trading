package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.calls.RestApiSecret
import eu.codlab.cex.spot.trading.models.AccountBalance
import eu.codlab.cex.spot.trading.models.OpenOrder
import eu.codlab.cex.spot.trading.models.OrderType
import eu.codlab.cex.spot.trading.models.PlaceOrder
import eu.codlab.cex.spot.trading.models.TransactionId
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer

class PrivateApi(
    clientId: String,
    apiKey: String,
    apiSecret: String
) {
    private val call = RestApiSecret(
        clientId,
        apiKey,
        apiSecret
    )

    suspend fun accountBalance() = call.call("balance/", AccountBalance.serializer())

    suspend fun openOrders(symbols: Pair<String, String>? = null): List<OpenOrder> {
        val url = if (null == symbols) {
            "open_orders/"
        } else {
            "open_orders/${symbols.first}/${symbols.second}"
        }

        return call.call(
            url,
            ListSerializer(OpenOrder.serializer())
        ) ?: emptyList()
    }

    /*
    // This is currently invalid
    suspend fun activeOrdersStatus(vararg list: Long) =
        activeOrdersStatus(list.asList())

    // This is currently invalid
    suspend fun activeOrdersStatus(list: List<Long>) =
        call.call(
            "active_orders_status",
            OrdersList(list),
            OrdersList.serializer(),
            String.serializer()
        )
     */

    suspend fun cancelOrder(transactionId: Long) =
        call.call(
            "cancel_order/",
            TransactionId(transactionId),
            TransactionId.serializer(),
            Boolean.serializer()
        )

    suspend fun cancelPairOrders(symbols: Pair<String, String>): List<Long> =
        call.call(
            "cancel_orders/${symbols.first}/${symbols.second}",
            ListSerializer(
                Long.serializer()
            )
        ) ?: emptyList()

    suspend fun placeOrder(
        symbols: Pair<String, String>,
        type: OrderType,
        amount: Double,
        price: Double
    ) = call.call(
        "place_order/${symbols.first}/${symbols.second}",
        PlaceOrder(
            type = type,
            amount = amount,
            price = price
        ),
        PlaceOrder.serializer(),
        Boolean.serializer()
    )
}