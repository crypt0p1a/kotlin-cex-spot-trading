package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyInfo(
    val currency: String,
    val walletDeposit: String,
    val walletWithdrawal: Boolean,
    val fiat: Boolean,
    val precision: Int,
    val walletPrecision: Int,
)
