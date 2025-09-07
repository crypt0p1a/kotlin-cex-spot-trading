package eu.codlab.cex.spot.trading.groups.funds

import kotlinx.serialization.Serializable

@Serializable
data class DepositToOrWithdrawalFromWalletRequest(
    /**
     * Transaction identifier assigned by Client.
     */
    val clientTxId: String,
    /**
     * Sub-account identifier, to which Client wants to deposit or withdraw funds. Empty string
     * value (“”) in this field is not allowed.
     */
    val accountId: String,
    /**
     * Crypto or fiat currency symbol for deposit or withdrawal.
     */
    val currency: String,
    /**
     * String representation of the amount to deposit or withdraw.
     */
    val amount: String,
)
