package eu.codlab.cex.spot.trading.groups.processing

import eu.codlab.cex.spot.trading.models.PercentageFloat
import kotlinx.serialization.Serializable

/**
 * This object contains details and limitations for deposit\withdrawal of cryptocurrency YYY via
 * blockchain X, including data about blockchain type, current availability to deposit\withdraw,
 * minimum deposit\withdrawal limit and external withdrawal fees.
 */
@Serializable
data class Blockchain(
    /**
     * Type of cryptocurrency YYY on blockchain X.
     */
    val type: String,
    /**
     * Describes current availability to deposit cryptocurrency YYY via blockchain X. Only "enabled"
     * or "disabled" values are allowed herein.
     */
    val deposit: Capability,
    /**
     * Minimum amount of cryptocurrency YYY which can be deposited from external wallet via
     * blockchain X.
     */
    val minDeposit: Double? = null,
    /**
     * Describes current availability to withdraw cryptocurrency YYY via blockchain X.
     * Only "enabled" or "disabled" values are allowed herein.
     */
    val withdrawal: Capability,
    /**
     * Minimum amount of cryptocurrency YYY which can be withdrawn to external wallet via
     * blockchain X.
     */
    val minWithdrawal: Double? = null,
    /**
     * Amount of withdrawal fee in cryptocurrency YYY, which which would be charged and subtracted
     * from withdrawal amount if blockchain X is used for withdrawal.
     */
    val withdrawalFee: Double? = null,
    @Deprecated("Disappeared in the official documentation")
    val withdrawalFeePercent: PercentageFloat? = null,
    val depositConfirmations: Int
)
