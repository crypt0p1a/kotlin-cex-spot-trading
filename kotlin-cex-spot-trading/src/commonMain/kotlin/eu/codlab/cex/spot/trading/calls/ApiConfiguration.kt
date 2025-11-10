package eu.codlab.cex.spot.trading.calls

data class ApiConfiguration(
    val rateLimitQueue: RateLimitQueue = RateLimitQueue()
)
