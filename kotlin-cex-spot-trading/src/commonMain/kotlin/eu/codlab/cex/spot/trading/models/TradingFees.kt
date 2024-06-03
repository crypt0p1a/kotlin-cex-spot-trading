package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

@Serializable
data class TradingFees(
    val tradingFee: Map<String, TradingFee>
)

@Serializable
data class TradingFee(
    val percent: PercentageFloat
)