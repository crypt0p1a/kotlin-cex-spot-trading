package eu.codlab.cex.spot.trading.bridge

import eu.codlab.cex.spot.trading.groups.candles.Candle
import eu.codlab.cex.spot.trading.groups.candles.CandleResolution
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPair
import eu.codlab.cex.spot.trading.groups.candles.CandlesFromPairs
import eu.codlab.cex.spot.trading.groups.history.trades.OrderTypeHistory
import eu.codlab.cex.spot.trading.groups.history.trades.TradeFromHistory
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistory
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithDate
import eu.codlab.cex.spot.trading.groups.history.trades.TradeHistoryRequestWithTrade
import eu.codlab.cex.spot.trading.groups.orderbook.OrderBook
import eu.codlab.cex.spot.trading.groups.orderbook.PriceAmount
import eu.codlab.cex.spot.trading.groups.orders.OrderSide
import eu.codlab.cex.spot.trading.models.DataType
import eu.codlab.cex.spot.trading.rust.bindings.Candle as RustCandle
import eu.codlab.cex.spot.trading.rust.bindings.CandleResolution as RustCandleResolution
import eu.codlab.cex.spot.trading.rust.bindings.CandlesFromPair as RustCandlesFromPair
import eu.codlab.cex.spot.trading.rust.bindings.CandlesFromPairs as RustCandlesFromPairs
import eu.codlab.cex.spot.trading.rust.bindings.DataType as RustDataType
import eu.codlab.cex.spot.trading.rust.bindings.OrderSide as RustOrderSide
import eu.codlab.cex.spot.trading.rust.bindings.OrderBook as RustOrderBook
import eu.codlab.cex.spot.trading.rust.bindings.PriceAmount as RustPriceAmount
import eu.codlab.cex.spot.trading.rust.bindings.TradeFromHistory as RustTradeFromHistory
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistory as RustTradeHistory
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistoryRequestWithDate as RustTradeHistoryRequestWithDate
import eu.codlab.cex.spot.trading.rust.bindings.TradeHistoryRequestWithTrade as RustTradeHistoryRequestWithTrade

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

internal fun CandlesFromPair.toRust() = RustCandlesFromPair(
    pair = pair,
    fromMs = fromISO?.unixMillisLong,
    toMs = toISO?.unixMillisLong,
    limit = limit,
    dataType = dataType.toRust(),
    resolution = resolution.toRust(),
)

internal fun CandlesFromPairs.toRust() = RustCandlesFromPairs(
    pairsList = pairsList,
    fromMs = fromISO?.unixMillisLong,
    toMs = toISO?.unixMillisLong,
    dataType = dataType.toRust(),
    resolution = resolution.toRust(),
)

internal fun DataType.toRust() = when (this) {
    DataType.BestAsk -> RustDataType.BEST_ASK
    DataType.BestBid -> RustDataType.BEST_BID
}

internal fun CandleResolution.toRust() = when (this) {
    CandleResolution.Res1m -> RustCandleResolution.RES1M
    CandleResolution.Res5m -> RustCandleResolution.RES5M
    CandleResolution.Res15m -> RustCandleResolution.RES15M
    CandleResolution.Res30m -> RustCandleResolution.RES30M
    CandleResolution.Res1h -> RustCandleResolution.RES1H
    CandleResolution.Res2h -> RustCandleResolution.RES2H
    CandleResolution.Res4h -> RustCandleResolution.RES4H
    CandleResolution.Res1d -> RustCandleResolution.RES1D
}

internal fun RustCandleResolution.toKotlin() = when (this) {
    RustCandleResolution.RES1M -> CandleResolution.Res1m
    RustCandleResolution.RES5M -> CandleResolution.Res5m
    RustCandleResolution.RES15M -> CandleResolution.Res15m
    RustCandleResolution.RES30M -> CandleResolution.Res30m
    RustCandleResolution.RES1H -> CandleResolution.Res1h
    RustCandleResolution.RES2H -> CandleResolution.Res2h
    RustCandleResolution.RES4H -> CandleResolution.Res4h
    RustCandleResolution.RES1D -> CandleResolution.Res1d
}

internal fun RustCandle.toKotlin() = Candle(
    timestamp = timestamp,
    open = open,
    high = high,
    low = low,
    close = close,
    volume = volume,
    resolution = resolution.toKotlin(),
    isClosed = isClosed,
    timestampISO = timestampIso,
)

internal fun OrderSide.toRust() = when (this) {
    OrderSide.Buy -> RustOrderSide.BUY
    OrderSide.Sell -> RustOrderSide.SELL
}

internal fun RustOrderSide.toKotlin() = when (this) {
    RustOrderSide.BUY -> OrderSide.Buy
    RustOrderSide.SELL -> OrderSide.Sell
}

internal fun TradeHistoryRequestWithTrade.toRust() = RustTradeHistoryRequestWithTrade(
    pair = pair,
    side = side?.toRust(),
    fromTradeId = fromTradeId,
    toTradeId = toTradeId,
    pageSize = pageSize,
)

internal fun TradeHistoryRequestWithDate.toRust() = RustTradeHistoryRequestWithDate(
    pair = pair,
    side = side?.toRust(),
    fromDateIso = fromDateISO,
    toDateIso = toDateISO,
    pageSize = pageSize,
)

internal fun RustTradeHistory.toKotlin() = TradeHistory(
    pageSize = pageSize,
    trades = trades.map { it.toKotlin() },
)

internal fun RustTradeFromHistory.toKotlin() = TradeFromHistory(
    side = when (side) {
        RustOrderSide.BUY -> OrderTypeHistory.Buy
        RustOrderSide.SELL -> OrderTypeHistory.Sell
    },
    dateISO = dateIso,
    price = price,
    amount = amount,
    tradeId = tradeId,
)
