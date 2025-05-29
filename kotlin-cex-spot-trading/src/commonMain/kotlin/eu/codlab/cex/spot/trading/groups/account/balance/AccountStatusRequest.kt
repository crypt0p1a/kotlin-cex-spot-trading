package eu.codlab.cex.spot.trading.groups.account.balance

import kotlinx.serialization.Serializable

@Serializable
data class AccountStatusRequest(
    /**
     * List of currencies for which Client wants to find out their accounts' balances. Currencies
     * should be in upper case and of string type. Each currency should be present only once in this
     * array. For example, ["USD", "BTC", "EUR", "BTC"] is not allowed. If this field is missing or
     * contains an empty array ([]), then it means Client wants to find out balances for all
     * available currencies.
     */
    val currencies: List<String>? = null,
    /**
     * List of account identifiers for which Client wants to find out their accounts' balances.
     * Empty string ("") value in this array represents Client’s main account. Each account
     * identifier should be of string type and should be present only once in this array. For
     * example, ["hallo", "", "account123", "hallo"] is not allowed. If this field is missing or if
     * it contains an empty array ([]), then it means Client wants to find out balances for all
     * accounts.
     */
    val accountIds: Set<String>? = null
)
