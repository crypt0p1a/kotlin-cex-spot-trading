package eu.codlab.cex.spot.trading.bridge

import eu.codlab.cex.spot.trading.models.ServerTime
import eu.codlab.cex.spot.trading.rust.bindings.ServerTime as RustServerTime

internal fun RustServerTime.toKotlin() = ServerTime(
    timestamp = timestamp,
    isoDate = isoDate,
)
