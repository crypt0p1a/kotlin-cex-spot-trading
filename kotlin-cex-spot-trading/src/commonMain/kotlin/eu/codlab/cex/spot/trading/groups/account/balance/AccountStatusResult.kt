package eu.codlab.cex.spot.trading.groups.account.balance

import eu.codlab.cex.spot.trading.groups.account.fees.TradingFeeBalancesForAccount
import kotlinx.serialization.Serializable

@Serializable
data class AccountStatusResult(
    /**
     * The currency in which balanceInConvertedCurrency is calculated by CEX.IO Spot Trading. By
     * default only "USD" value is allowed herein.
     */
    val convertedCurrency: String,
    /**
     * This object contains details about Client's currencies' balances as to each account which
     * satisfies request criteria. It might be empty object ("{}"), but this field should be present
     * anyway and it should contain an object. If this field contains an empty object, then it means
     * Client has no accounts which satisfy Client’s request criteria.
     *
     * Main Map's Key : a specific account
     * Account's Key : the currency hold
     */
    val balancesPerAccounts: Map<String, Map<String, BalanceForAccount>>,
    /**
     * This object contains details about Client’s trading fee balances. This object can be absent
     * if Client has no available trading fee balances because they were never obtained OR if all of
     * obtained trading fee balances have already expired or were fully utilized.
     */
    val tradingFeeBalancesPerAccounts: Map<String, Map<String, TradingFeeBalancesForAccount>>? = null
)
