package eu.codlab.cex.spot.trading.groups.account.fees

import kotlinx.serialization.Serializable

@Serializable
data class TradingFeeBalancesForAccount(
    /**
     * Available amount of YYY currency trading fee balance, expirationDate of which has not been
     * reached yet and which can be used for trading fee utilization.
     */
    val balance: Double,
    /**
     * Expiration date of YYY currency trading fee balance. Format: YYYY-MM-DDTHH:MM:SS.sssZ .This
     * field can be absent for “lifetimeBonus” trading fee account, which means YYY trading fee
     * balance amount has no set expiration date.
     */
    val expirationDate: String
)
