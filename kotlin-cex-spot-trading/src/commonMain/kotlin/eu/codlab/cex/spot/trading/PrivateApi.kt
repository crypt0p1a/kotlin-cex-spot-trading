package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.calls.RestApiSecret
import eu.codlab.cex.spot.trading.models.AccountBalance
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

    suspend fun activeOrdersStatus() = call.call("active_orders_status", String.serializer())
}