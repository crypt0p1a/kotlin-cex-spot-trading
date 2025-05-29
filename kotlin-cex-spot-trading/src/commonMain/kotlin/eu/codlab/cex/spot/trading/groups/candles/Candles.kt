package eu.codlab.cex.spot.trading.groups.candles

sealed class Candles {
    internal abstract fun toGetCandles(): GetCandles
}
