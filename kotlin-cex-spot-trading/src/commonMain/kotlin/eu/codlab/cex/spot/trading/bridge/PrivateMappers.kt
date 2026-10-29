package eu.codlab.cex.spot.trading.bridge

import eu.codlab.cex.spot.trading.groups.fees.FeeStrategy
import eu.codlab.cex.spot.trading.groups.fees.FeeVolume
import eu.codlab.cex.spot.trading.groups.fees.StrategyConfig
import eu.codlab.cex.spot.trading.groups.volume.Volume
import eu.codlab.cex.spot.trading.models.TradingFee
import eu.codlab.cex.spot.trading.models.TradingFees
import eu.codlab.cex.spot.trading.rust.bindings.FeeStrategy as RustFeeStrategy
import eu.codlab.cex.spot.trading.rust.bindings.FeeVolume as RustFeeVolume
import eu.codlab.cex.spot.trading.rust.bindings.StrategyConfig as RustStrategyConfig
import eu.codlab.cex.spot.trading.rust.bindings.TradingFee as RustTradingFee
import eu.codlab.cex.spot.trading.rust.bindings.TradingFees as RustTradingFees
import eu.codlab.cex.spot.trading.rust.bindings.Volume as RustVolume

internal fun RustTradingFees.toKotlin() = TradingFees(
    tradingFee = tradingFee.mapValues { it.value.toKotlin() },
)

internal fun RustTradingFee.toKotlin() = TradingFee(
    percent = percent,
)

internal fun RustFeeStrategy.toKotlin() = FeeStrategy(
    strategyConfig = strategyConfig.toKotlin(),
)

internal fun RustStrategyConfig.toKotlin() = StrategyConfig(
    default = defaultFees.map { it.toKotlin() },
)

internal fun RustFeeVolume.toKotlin() = FeeVolume(
    fee = fee,
    volume = volume.toInt(),
)

internal fun RustVolume.toKotlin() = Volume(
    period = period,
    volume = volume,
    currency = currency,
)
