package eu.codlab.cex.spot.trading.groups.funds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DepositToOrWithdrawFromWalletResult(
    /**
     * Transaction identifier assigned by Client.
     */
    val clientTxId: String,
    /**
     * Sub-account identifier, to which Client initiated deposit or withdraw funds.
     */
    val accountId: String,
    /**
     * Crypto or fiat currency symbol for deposit or withdraw.
     */
    val currency: String,
    /**
     * Deposit or withdraw transaction status. Allowed values - "rejected", "pending", "approved".
     */
    val status: ApprovalStatus
)

@Serializable
enum class ApprovalStatus {
    @SerialName("rejected")
    REJECTED,

    @SerialName("pending")
    PENDING,

    @SerialName("approved")
    APPROVED
}
