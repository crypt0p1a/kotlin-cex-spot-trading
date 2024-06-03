package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProcessingInfo(
    val name: String,
    val blockchains: Map<String, Blockchain>
)

@Serializable
data class Blockchain(
    val type: String,
    val deposit: Capability,
    val minDeposit: Double? = null,
    val withdrawal: Capability,
    val minWithdrawal: Double? = null,
    val withdrawalFee: Double? = null,
    val withdrawalFeePercent: PercentageFloat? = null,
    val depositConfirmations: Int
)

@Serializable
enum class Capability {
    @SerialName("enabled")
    Enabled,

    @SerialName("disabled")
    Disabled
}
