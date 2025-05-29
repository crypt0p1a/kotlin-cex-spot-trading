package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    /**
     * Order identifier assigned by CEX.IO Spot Trading. If both fields "orderId" and
     * "clientOrderId" are present, then CEX.IO Spot Trading ignores "orderId" field. If this field
     * is present (and "clientOrderId" is absent), then it means Client wants to see the status of
     * the exact order. In this case, CEX.IO Spot Trading ignores all other parameters in "data"
     * field.
     */
    val orderId: String? = null,
    /**
     * If value is true, then it means Client wants to get his completed (archived) orders.
     * "Completed" means that order is in one of its final statuses. If value is false or if this
     * field is missing, it means Client wants to get his open orders. Value should be in boolean
     * type. So values like null, 0, 1, "true", "hallo" and similar are not allowed.
     */
    val archived: Boolean? = null,
    /**
     * Currency pair, for which Client wants to find his orders. Pair should contain two currencies
     * in upper case divided by "-" symbol. Pair should be listed in traditional direction. For
     * example, "BTC-USD", but not "USD-BTC". If this field is missing, or if it contains empty
     * string (""), or null, then it means Client wants to find his orders for all pairs.
     */
    val pair: String? = null,
    /**
     * Side of the orders, for which Client wants to find his orders.
     */
    val side: String? = null,
    /**
     * List of account identifiers, for which Client wants to find his orders. Empty string ("") or
     * null value in this array represents Client’s main account. Each account identifier should be
     * present only once in this array. For example, ["hallo", "", "superhat", "hallo"] is not
     * allowed. If this field is missing or if it contains an empty array ([]), then it means Client
     * wants to find his orders for all accounts.
     */
    val accountIds: List<String>? = null,
    /**
     * Sort order of the result set. The result array is sorted by serverCreateTimestamp. "ASC" -
     * ascending order, "DESC" - descending order. If this field is missing then the default sort
     * order is "DESC".
     */
    val sortOrder: Order = Order.Desc,
    /**
     * Because the result might contain too many orders, Client should specify which portion of the
     * result list he wants to get as a response to this request. This parameter limits the maximum
     * number of orders in the result for this request. If this field is missing, then the default
     * value of 1000 is used. This value cannot be greater than 1000.
     */
    val pageSize: Int? = null,
    /**
     * Because the result might contain too many orders, Client should specify which portion of the
     * result list he wants to get as a response to this request. Result list is chunked into pages
     * for not more than data.pageSize orders per each page. This parameter specifies, which page
     * number of the result set Client wants to see as the response to this request. First page
     * number is 0. If this field is missing, then the default value of 0 is used. This value
     * cannot be lower than 0.
     */
    val pageNumber: Int? = null,
    // TODO : add serverCreate
) {
    internal fun to() = OrderRequestInternal(
        orderId = orderId,
        archived = archived,
        pair = pair,
        side = side,
        accountIds = accountIds,
        sortOrder = sortOrder,
        pageSize = pageSize,
        pageNumber = pageNumber
    )
}

@Serializable
internal data class OrderRequestInternal(
    val clientOrderId: String? = null,
    val orderId: String? = null,
    val archived: Boolean? = null,
    val pair: String? = null,
    val side: String? = null,
    val accountIds: List<String>? = null,
    // TODO add serverCreate
    val sortOrder: Order = Order.Desc,
    val pageSize: Int? = null,
    val pageNumber: Int? = null
)

@Serializable
enum class Order {
    @SerialName("DESC")
    Desc,

    @SerialName("ASC")
    Asc
}
