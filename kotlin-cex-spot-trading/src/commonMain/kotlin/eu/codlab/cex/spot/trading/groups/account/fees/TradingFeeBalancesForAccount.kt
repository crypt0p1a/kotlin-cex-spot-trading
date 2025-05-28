package eu.codlab.cex.spot.trading.groups.account.fees

import kotlinx.serialization.Serializable

@Serializable
data class TradingFeeBalancesForAccount(
    val balance: Double,
    val expirationDate: String
)