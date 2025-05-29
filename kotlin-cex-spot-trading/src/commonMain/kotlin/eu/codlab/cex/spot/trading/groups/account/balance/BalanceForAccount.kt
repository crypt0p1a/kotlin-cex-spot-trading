package eu.codlab.cex.spot.trading.groups.account.balance

import kotlinx.serialization.Serializable

@Serializable
data class BalanceForAccount(
    /**
     * Current X account balance in YYY currency. It includes balance which is reserved (locked) for
     * active orders (please find this information in "balanceOnHold" field).
     */
    val balance: Double,
    /**
     * Current X account balance in YYY currency which is reserved (locked) for active orders.
     */
    val balanceOnHold: Double,
    /**
     * Equivalent in converted currency of current YYY currency balance on Client's X account. This
     * amount is calculated according to CEX.IO Spot Trading indicative exchange rate of YYY
     * currency to base currency. If current YYY currency balance on Client's X account is zero OR
     * if CEX.IO Spot Trading failed to calculate such equivalent in converted currency, then this
     * field would be missing.
     */
    val balanceInConvertedCurrency: Double? = null,
)
