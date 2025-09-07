package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.groups.account.balance.AccountBalance
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusRequest
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusResult
import eu.codlab.cex.spot.trading.groups.account.create.CreateAccountResult
import eu.codlab.cex.spot.trading.groups.fees.FeeStrategy
import eu.codlab.cex.spot.trading.groups.funds.DepositToOrWithdrawFromWalletResult
import eu.codlab.cex.spot.trading.groups.funds.DepositToOrWithdrawalFromWalletRequest
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionHistory
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionHistoryRequest
import eu.codlab.cex.spot.trading.groups.orders.cancel.CancelOrder
import eu.codlab.cex.spot.trading.groups.orders.news.NewOrder
import eu.codlab.cex.spot.trading.groups.orders.news.NewOrderAnswer
import eu.codlab.cex.spot.trading.groups.pairsinfo.Pairs
import eu.codlab.cex.spot.trading.groups.volume.Volume
import eu.codlab.cex.spot.trading.models.OrderRequest
import eu.codlab.cex.spot.trading.models.OrderResult
import eu.codlab.cex.spot.trading.models.TradingFees

interface IPrivateApi : ICommonApi {
    /**
     * This method indicates current fees at specific moment of time with consideration of Client'
     * up-to-date 30d volume and day of week (fees can be different for e.g. on weekends).
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-current-fee
     */
    suspend fun getMyCurrentFee(pairs: Pairs): TradingFees

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
    suspend fun getFeeStrategy(): FeeStrategy

    /**
     * This request allows Client to receive his trading volume for the last 30 days in USD equivalent.
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-volume
     */
    suspend fun getMyVolume(): Volume

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
    suspend fun createAccount(accountId: String, currency: String): CreateAccountResult

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
    suspend fun getMyAccountStatus(request: AccountStatusRequest): AccountStatusResult

    /**
     * This request allows Client to receive his CEX.IO Wallet balances, which can be useful for
     * Client to check his current Wallet balances before depositing funds from to Spot Trading
     * sub-accounts or after withdrawing funds from Spot Trading sub-accounts to CEX.IO Wallet
     * account.
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-wallet-balance
     */
    suspend fun accountBalance(): Map<String, AccountBalance>

    /**
     * This request allows Client to find out info about his orders.
     *
     * Duplicated clientOrderIds
     * If Client has several orders with the same clientOrderId (if orders were created in a
     * significant period of time) and will query orders by clientOrderId, response will return an
     * array with all orders’ details in which requested clientOrderId has been indicated by the
     * Client.
     */
    suspend fun orders(request: OrderRequest): List<OrderResult>

    /**
     * This request allows Client to find out info about his orders.
     *
     * Duplicated clientOrderIds
     * If Client has several orders with the same clientOrderId (if orders were created in a
     * significant period of time) and will query orders by clientOrderId, response will return an
     * array with all orders’ details in which requested clientOrderId has been indicated by the
     * Client.
     */
    suspend fun orders(orderId: String): OrderResult?

    /**
     * This request allows Client to find out info about his orders.
     *
     * Duplicated clientOrderIds
     * If Client has several orders with the same clientOrderId (if orders were created in a
     * significant period of time) and will query orders by clientOrderId, response will return an
     * array with all orders’ details in which requested clientOrderId has been indicated by the
     * Client.
     */
    suspend fun orders(): List<OrderResult>

    /**
     * This request allows Client to find out info about his orders.
     *
     * Duplicated clientOrderIds
     * If Client has several orders with the same clientOrderId (if orders were created in a
     * significant period of time) and will query orders by clientOrderId, response will return an
     * array with all orders’ details in which requested clientOrderId has been indicated by the
     * Client.
     */
    suspend fun orders(symbols: Pair<String, String>): List<OrderResult>

    /**
     * This method works only with CEX.IO Spot Trading sub-accounts.
     *
     * Client can place new orders via REST API by using Do My New Order Request. The parameters
     * and samples of such requests are shown in this section.
     *
     * Response message indicates the last up-to-date status of order which is available in the
     * system at the moment of sending the response.
     *
     * If the Client did not receive a Response message to Do My New Order Request -
     * the Client can query current status of the order by using Get My Orders Request with
     * clientOrderId parameter.
     *
     * When sending a request for new order, it is highly recommended to use clientOrderId parameter
     * which corresponds to the specific new order request on the client's side. Spot Trading
     * protects multiple placing of orders with the same clientOrderId for a reasonable period of
     * time.
     *
     * If more than one new orders with identical clientOrderId and other order parameters are
     * identified - Spot Trading places only the first order and returns the status of such order to
     * the Client in response to the second and subsequent new order requests with the same
     * parameters. If orders with identical clientOrderId but with different other order parameters
     * are identified - Spot Trading processes only the first order and rejects the second and
     * subsequent new order requests with the same clientOrderID but with different other order
     * parameters. Nevertheless, if Client creates more than one orders with same clientOrderId in a
     * significant period of time, order with same clientOrderId can be accepted by the system. It's
     * Client's responsibility to control unique indication of clientOrderIds.
     *
     * Order limitations
     * The system has a limit on the number of active (open) orders. By default, Client can have a
     * maximum of 20 active taker orders. Once limit is reached, the system will reject any new
     * orders with a reject reason “Too many active orders” until the total number of active orders
     * is below the limit.
     *
     * Important notice regarding Market orders
     * CEX.IO Spot Trading can partially execute market orders in order to prevent Client's possible
     * loss. Market orders that were executed but not completely filled will have "CANCELED" status.
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-new-order
     */
    suspend fun newOrder(
        request: NewOrder
    ): NewOrderAnswer

    /**
     * Client can cancel orders.
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-cancel-order
     *
     * Will throw in case of errors
     */
    suspend fun cancelOrder(
        request: CancelOrder
    )

    /**
     * Client can cancel all open orders via REST API.
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-cancel-all-orders
     */
    suspend fun cancelOrders(): List<String>

    /**
     * This request allows Client to find his financial transactions (deposits, withdrawals,
     * internal transfers, commissions or trades).
     *
     * https://trade.cex.io/docs/#rest-private-api-calls-transaction-history
     */
    suspend fun transactionHistory(
        request: TransactionHistoryRequest
    ): List<TransactionHistory>

    suspend fun fundsDepositFromWallet(
        request: DepositToOrWithdrawalFromWalletRequest
    ): DepositToOrWithdrawFromWalletResult

    suspend fun fundsWithdrawToWallet(
        request: DepositToOrWithdrawalFromWalletRequest
    ): DepositToOrWithdrawFromWalletResult
}
