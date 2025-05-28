package eu.codlab.cex.spot.trading.groups.account.balance

import kotlinx.serialization.Serializable

@Serializable
data class BalanceForAccount(
    val balance: Double,
    val balanceOnHold: Double,
    val balanceInConvertedCurrency: Double? = null,
)