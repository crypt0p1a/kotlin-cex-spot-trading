package eu.codlab.cex.spot.trading.groups.account.create

import kotlinx.serialization.Serializable

@Serializable
data class CreateAccountRequest(
    val accountId: String,
    val currency: String
)
