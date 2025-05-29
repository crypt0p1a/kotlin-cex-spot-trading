package eu.codlab.cex.spot.trading.groups.currency

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyInfo(
    /**
     * Currency name.
     */
    val currency: String,
    /**
     * Describes current availability to deposit currency X to CEX.IO Spot Trading from CEX.IO
     * Wallet. Only true or false values are allowed herein.
     */
    val walletDeposit: String,
    /**
     * Describes current availability to withdraw currency X from CEX.IO Spot Trading to CEX.IO
     * Wallet. Only true or false values are allowed herein.
     */
    val walletWithdrawal: Boolean,
    /**
     * Indicates if the currency is a fiat currency or cryptocurrency. If the value is true, then
     * indicated currency is fiat. If the value is false, then indicated currency is cryptocurrency.
     */
    val fiat: Boolean,
    /**
     * Number of decimal places in amounts of specific currency used inside CEX.IO Spot Trading
     * system (e.g. for internal transfers, executed amounts in orders etc.).
     */
    val precision: Int,
    /**
     * If the value of this parameter is a number, then it describes the number of decimal places
     * in amounts of specific currency used for transfers to or out of CEX.IO Spot Trading system
     * (e.g. for deposits\withdrawals from\to CEX.IO Wallet or external addresses). If the value is
     * null, then deposits and withdrawals of specific currency from\to CEX.IO Wallet are not
     * available.
     */
    val walletPrecision: Int,
)
