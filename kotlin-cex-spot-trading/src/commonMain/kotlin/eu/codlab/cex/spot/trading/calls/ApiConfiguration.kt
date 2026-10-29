package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rust.ApiConfiguration as RustApiConfiguration

data class ApiConfiguration(
    val rateLimitQueue: RateLimitQueue = RateLimitQueue()
) {
    internal val rust = RustApiConfiguration(rateLimitQueue.rust)
}
