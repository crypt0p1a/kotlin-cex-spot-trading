package eu.codlab.cex.spot.trading.bridge

import eu.codlab.cex.spot.trading.models.TradingFee
import eu.codlab.cex.spot.trading.models.TradingFees
import eu.codlab.cex.spot.trading.rust.bindings.TradingFee as RustTradingFee
import eu.codlab.cex.spot.trading.rust.bindings.TradingFees as RustTradingFees

internal fun RustTradingFees.toKotlin() = TradingFees(
    tradingFee = tradingFee.mapValues { it.value.toKotlin() },
)

internal fun RustTradingFee.toKotlin() = TradingFee(
    percent = percent,
)
