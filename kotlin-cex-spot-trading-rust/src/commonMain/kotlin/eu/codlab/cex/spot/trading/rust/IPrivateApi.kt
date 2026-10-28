package eu.codlab.cex.spot.trading.rust

import eu.codlab.cex.spot.trading.rust.bindings.AccountBalance
import eu.codlab.cex.spot.trading.rust.bindings.AccountStatusRequest
import eu.codlab.cex.spot.trading.rust.bindings.AccountStatusResult
import eu.codlab.cex.spot.trading.rust.bindings.CancelOrder
import eu.codlab.cex.spot.trading.rust.bindings.CreateAccountResult
import eu.codlab.cex.spot.trading.rust.bindings.DepositToOrWithdrawFromWalletResult
import eu.codlab.cex.spot.trading.rust.bindings.DepositToOrWithdrawalFromWalletRequest
import eu.codlab.cex.spot.trading.rust.bindings.FeeStrategy
import eu.codlab.cex.spot.trading.rust.bindings.NewOrder
import eu.codlab.cex.spot.trading.rust.bindings.NewOrderAnswer
import eu.codlab.cex.spot.trading.rust.bindings.OrderRequest
import eu.codlab.cex.spot.trading.rust.bindings.OrderResult
import eu.codlab.cex.spot.trading.rust.bindings.TradingFees
import eu.codlab.cex.spot.trading.rust.bindings.TransactionHistory
import eu.codlab.cex.spot.trading.rust.bindings.TransactionHistoryRequest
import eu.codlab.cex.spot.trading.rust.bindings.Volume

@Suppress("TooManyFunctions")
interface IPrivateApi : ICommonApi {
    /**
     * Current fees, depending on the 30d volume and the day of the week
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-current-fee
     */
    suspend fun getMyCurrentFee(pairs: List<String>): TradingFees

    /**
     * All the fee options which could be applied to the Client
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-fee-strategy
     */
    suspend fun getFeeStrategy(): FeeStrategy

    /**
     * Trading volume of the last 30 days, in USD
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-volume
     */
    suspend fun getMyVolume(): Volume

    /**
     * Creates a sub-account, up to 5 accounts including the main one
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-create-account
     */
    suspend fun createAccount(accountId: String, currency: String): CreateAccountResult

    /**
     * Balances per sub-account and currency, with the amounts locked in open orders
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-account-status-v3
     */
    suspend fun getMyAccountStatus(
        request: AccountStatusRequest = AccountStatusRequest()
    ): AccountStatusResult

    /**
     * Balances of the CEX.IO Wallet, keyed by currency
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-wallet-balance
     */
    suspend fun accountBalance(): Map<String, AccountBalance>

    /**
     * Orders matching the request, the open orders of all the pairs by default
     */
    suspend fun orders(request: OrderRequest = OrderRequest()): List<OrderResult>

    /**
     * The order with this CEX.IO identifier
     */
    suspend fun orders(orderId: String): OrderResult?

    /**
     * The open orders of a pair
     */
    suspend fun orders(symbols: Pair<String, String>): List<OrderResult>

    /**
     * Places an order on a Spot Trading sub-account. Prefer setting a clientOrderId: the orders
     * sent again with the same one are only placed once
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-new-order
     */
    suspend fun newOrder(request: NewOrder): NewOrderAnswer

    /**
     * Cancels an order, throws otherwise
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-cancel-order
     */
    suspend fun cancelOrder(request: CancelOrder)

    /**
     * Cancels all the open orders, returns their client order ids
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-cancel-all-orders
     */
    suspend fun cancelOrders(): List<String>

    /**
     * Deposits, withdrawals, internal transfers, commissions or trades
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-transaction-history
     */
    suspend fun transactionHistory(request: TransactionHistoryRequest): List<TransactionHistory>

    /**
     * Moves funds from the CEX.IO Wallet to a Spot Trading sub-account
     */
    suspend fun fundsDepositFromWallet(
        request: DepositToOrWithdrawalFromWalletRequest
    ): DepositToOrWithdrawFromWalletResult

    /**
     * Moves funds from a Spot Trading sub-account to the CEX.IO Wallet
     */
    suspend fun fundsWithdrawToWallet(
        request: DepositToOrWithdrawalFromWalletRequest
    ): DepositToOrWithdrawFromWalletResult
}
