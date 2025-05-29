package eu.codlab.cex.spot.trading.groups.account.balance

import kotlinx.serialization.Serializable

@Serializable
data class AccountStatusRequest(
    val currencies: List<String>? = null,
    val accountIds: Set<String>? = null
)
