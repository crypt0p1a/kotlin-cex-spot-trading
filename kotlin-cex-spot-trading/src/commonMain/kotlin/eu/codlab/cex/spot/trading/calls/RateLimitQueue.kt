package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rust.RateLimitQueue as RustRateLimitQueue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Token bucket shared by the APIs which receive it: each call consumes a token, a new token is
 * created every [apiTokenCreationInterval] up to [apiTokenPoolMaximum] tokens. The calls are
 * executed one after the other by the Rust implementation.
 */
class RateLimitQueue(
    apiTokenPoolMaximum: UInt = 10U,
    apiTokenCreationInterval: Duration = 2.seconds,
    logger: (tag: String, msg: String) -> Unit = { _, _ -> },
) : AutoCloseable {
    internal val rust = RustRateLimitQueue(apiTokenPoolMaximum, apiTokenCreationInterval, logger)

    override fun close() = rust.close()
}
