package eu.codlab.cex.spot.trading.groups.orders

import kotlinx.serialization.Serializable


@Serializable
enum class OrderType {
    Limit,
    Market
}