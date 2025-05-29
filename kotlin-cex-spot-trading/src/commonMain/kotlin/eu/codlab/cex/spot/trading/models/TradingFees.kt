package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.Serializable

/**
 * Represents trading fees for all supported trading pairs.
 */
@Serializable
data class TradingFees(
    val tradingFee: Map<String, TradingFee>
)

/**
 * Represents data about trading fee for specific XXX-YYY trading pair.
 */
@Serializable
data class TradingFee(
    /**
     * Represents fee percent for XXX-YYY trading pair.
     */
    val percent: PercentageFloat
)
