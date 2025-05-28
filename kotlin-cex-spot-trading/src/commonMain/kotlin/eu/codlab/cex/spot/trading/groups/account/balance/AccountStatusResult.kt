package eu.codlab.cex.spot.trading.groups.account.balance

import eu.codlab.cex.spot.trading.groups.account.fees.TradingFeeBalancesForAccount
import kotlinx.serialization.Serializable

@Serializable
data class AccountStatusResult(
    val convertedCurrency: String,
    val balancesPerAccounts: Map<String, Map<String, BalanceForAccount>>,
    val tradingFeeBalancesPerAccounts: Map<String, Map<String, TradingFeeBalancesForAccount>>? = null
)