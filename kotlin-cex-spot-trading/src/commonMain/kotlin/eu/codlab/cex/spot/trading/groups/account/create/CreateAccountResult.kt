package eu.codlab.cex.spot.trading.groups.account.create

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountResult(
    val accountId: String
)