package eu.codlab.cex.spot.trading.groups.history.transactions

import eu.codlab.cex.spot.trading.models.Order
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionHistoryRequest(
    /**
     * Account identifier, for which Client wants to find transactions. Empty string ("") or null
     * value in this field represents Client’s main account. If this field is missing, then it means
     * Client wants to find transactions for the main account and all sub-accounts.
     */
    val accountId: String,
    /**
     * If this field is present and contains one of the allowed values, then it means Client wants
     * to get only transactions related to specified operation type. Allowed values are:
     *  - "trade",
     *  - "deposit",
     *  - "withdraw",
     *  - "internalTransfer,
     *  - "commission".
     *
     *  If this field is missing or if this field is present but contains an empty string (""),
     *  then it means Client wants to get transactions for all operation types.
     */
    val type: TransactionType,
    /**
     * UTC timestamp in milliseconds. Represents the earliest moment in time when transactions were
     * created. In the result set transactions’ timestamp field value should be greater than or
     * equal to (>=) dateFrom. If the request contains values in both fields dateFrom and dateTo,
     * then the value in the field dateFrom should be less than the value in the field dateTo.
     */
    val dateFrom: Long? = null,
    /**
     * UTC timestamp in milliseconds. Represents the latest moment in time when transactions were
     * created. In the result set transactions’ timestamp field value should be less than dateTo.
     * If the request contains values in both fields dateFrom and dateTo, then the value in the
     * field dateTo should be greater than the value in the field dateFrom.
     */
    val dateTo: Long? = null,
    /**
     * Sort order of the result set. The result array is sorted by "timestamp" field. Allowed values:
     * "ASC" - ascending order
     * "DESC" - descending order.
     *
     * If this field is missing then the default sort order is "DESC", so recently created
     * transactions come first and oldest transactions come last.
     */
    val sortOrder: Order? = null,
    /**
     * Because the result might contain too many transactions, Client should specify, which portion
     * of the result list he wants to get as a response to this request. This parameter limits the
     * maximum number of transactions in the result for this request and cannot be greater than 100.
     * If this field is missing, then the default value of 100 is used. If this field contains one
     * of the allowed values and, simultaneously, the pageNumber field is missing, then the default
     * pageNumber value is applied. Specifying the value in the field pageSize is mandatory if the
     * value in the field pageNumber is also sent in the Client's request.
     */
    val pageSize: Int? = null,
    /**
     * Because the result might contain too many transactions, Client should specify, which portion
     * of the result list he wants to get as a response to this request. Result list is chunked into
     * pages for not more than data.pageSize transactions per each page. This parameter specifies,
     * which page number of the result set Client wants to see as the response to this request.
     * First page number is 1. If this field is missing, then the default value of 1 is used. This
     * value cannot be lower than 1. If any valid value is specified in this field, then it is
     * mandatory to also send the value in the field pageSize in the Client’s request.
     */
    val pageNumber: Int? = null
)

@Serializable
enum class TransactionType {
    @SerialName("trade")
    Trade,

    @SerialName("deposit")
    Deposit,

    @SerialName("withdraw")
    Withdraw,

    @SerialName("internalTransfer")
    InternalTransfer,

    @SerialName("commission")
    Commission
}

@Serializable
data class TransactionHistory(
    /**
     * Unique identifier of transaction.
     */
    val transactionId: String,
    /**
     * Represents server timestamp when this transaction happened. Format: YYYY-MM-DDTHH:MM:SS.sssZ .
     */
    val timestamp: String,
    /**
     * Represents the Account ID.
     */
    val accountId: String,
    /**
     * Represents the type of this operation. Allowed values are "trade", "deposit", "withdraw", "internalTransfer", "commission".
     */
    val type: TransactionType,
    /**
     * Represents amount of the transaction.
     */
    val amount: Double,
    /**
     * Represents transaction details.
     */
    val details: String,
    /**
     * Represents the currency of the transaction.
     */
    val currency: String
)
