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
    private val call: RestApiSecret,
) {
    /**
     * This request allows Client to create new sub-account.
     *
     * By default Client can have up to 5 sub-accounts, including main account.
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-create-account
     *
     * @param accountId New unique sub-account name which Client requests to create. The value in
     * this field can contain only lower and uppercase Latin letters, numbers, underscore ("_") or
     * hyphen ("-").
     * @param currency Represents crypto or fiat currency symbol which Client expects to be initialy
     * deposited to new sub-account (e.g. from Client's other Spot Trading sub-account, from CEX.IO
     * Wallet acount, from external crypto wallet etc.).
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
     * By using Account Status V3 method, Client can find out current balance and it’s indicative
     * equivalent in converted currency (by default “USD”), amounts locked in open (active) orders
     * as to each sub-account and currency.
     *
     * If trading fee balance is available for Client, then response will also contain general
     * trading fee balance data such as promo name, currency name, total balance and expiration date
     * of this promo on Trading Fee Balance.
     *
     * It’s Client’s responsibility to track his sub-accounts available trading balance as current
     * sub-account balance reduced by the balance amount locked in open (active) orders on
     * sub-account.
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-account-status-v3
     */
    suspend fun getMyAccountStatus(request: AccountStatusRequest) = call.call(
        "get_my_account_status_v3",
        request,
        AccountStatusRequest.serializer(),
        AccountStatusResult.serializer()
    )

    /**
     * This request allows Client to receive his CEX.IO Wallet balances, which can be useful for
     * Client to check his current Wallet balances before depositing funds from to Spot Trading
     * sub-accounts or after withdrawing funds from Spot Trading sub-accounts to CEX.IO Wallet
     * account.
     *
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
     * Fee Strategy returns all fee options, which could be applied for Client, considering Client’s
     * trading volume, day of week, pairs, group of pairs etc.
     *
     * This method provides information about general fee strategy, which includes all possible
     * trading fee values that can be applied for Client. To receive current trading fees, based on
     * Client's current 30d trading volume, Client should use [Current Fee] method. To receive
     * current 30d trading volume, Client should use [Volume] method.
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-fee-strategy
     */
    suspend fun getFeeStrategy() = call.call(
        "get_fee_strategy",
        FeeStrategy.serializer()
    )
}
