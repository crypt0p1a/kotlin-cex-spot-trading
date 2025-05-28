package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val orderId: String? = null,
    val archived: Boolean? = null,
    val pair: String? = null,
    val side: String? = null,
    val accountIds: List<String>? = null,
    //TODO add serverCreate
    val sortOrder: Order = Order.Desc,
    val pageSize: Int? = null,
    val pageNumber: Int? = null
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
    //TODO add serverCreate
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