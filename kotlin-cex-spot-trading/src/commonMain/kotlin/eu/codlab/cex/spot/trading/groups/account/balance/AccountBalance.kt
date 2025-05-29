package eu.codlab.cex.spot.trading.groups.account.balance

import kotlinx.serialization.Serializable

@Serializable
data class AccountBalance(
    /**
     * Current CEX.IO Wallet account balance in XXX currency.
     */
    val balance: Double,
)
