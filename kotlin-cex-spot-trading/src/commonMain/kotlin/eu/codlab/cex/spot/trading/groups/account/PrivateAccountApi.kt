package eu.codlab.cex.spot.trading.groups.account

import eu.codlab.cex.spot.trading.calls.RestApiSecret
import eu.codlab.cex.spot.trading.groups.account.balance.AccountBalance
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusRequest
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusResult
import eu.codlab.cex.spot.trading.groups.account.create.CreateAccountRequest
import eu.codlab.cex.spot.trading.groups.account.create.CreateAccountResult
import eu.codlab.cex.spot.trading.groups.fees.FeeStrategy
import eu.codlab.cex.spot.trading.groups.pairsinfo.Pairs
import eu.codlab.cex.spot.trading.models.TradingFees
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
        "get_my_wallet_balance",
        MapSerializer(
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
