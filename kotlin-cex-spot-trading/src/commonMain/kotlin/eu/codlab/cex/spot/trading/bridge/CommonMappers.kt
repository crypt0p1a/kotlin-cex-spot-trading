package eu.codlab.cex.spot.trading.bridge

import eu.codlab.cex.spot.trading.groups.orderbook.OrderBook
import eu.codlab.cex.spot.trading.groups.orderbook.PriceAmount
import eu.codlab.cex.spot.trading.rust.bindings.OrderBook as RustOrderBook
import eu.codlab.cex.spot.trading.rust.bindings.PriceAmount as RustPriceAmount

internal fun RustOrderBook.toKotlin() = OrderBook(
    timestamp = timestamp,
    currency1 = currency1,
    currency2 = currency2,
    bids = bids.map { it.toKotlin() },
    asks = asks.map { it.toKotlin() },
)

internal fun RustPriceAmount.toKotlin() = PriceAmount(
    price = price,
    amount = amount,
)
