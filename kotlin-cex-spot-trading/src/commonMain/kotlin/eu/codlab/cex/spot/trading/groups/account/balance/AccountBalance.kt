package eu.codlab.cex.spot.trading.groups.account.balance

import kotlinx.serialization.Serializable

@Serializable
data class AccountBalance(
    val balance: Double,
)
