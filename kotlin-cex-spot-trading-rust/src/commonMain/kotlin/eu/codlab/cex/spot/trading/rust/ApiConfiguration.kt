package eu.codlab.cex.spot.trading.rust

import eu.codlab.cex.spot.trading.rust.bindings.CexLogger
import eu.codlab.cex.spot.trading.rust.bindings.CexRateLimiter
import eu.codlab.cex.spot.trading.rust.bindings.defaultRestOptions
import eu.codlab.cex.spot.trading.rust.bindings.RestOptions
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
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
    internal val native = CexRateLimiter(
        apiTokenPoolMaximum,
        apiTokenCreationInterval.inWholeMilliseconds.toULong(),
        object : CexLogger {
            override fun log(tag: String, message: String) = logger(tag, message)
        }
    )

    override fun close() = native.close()
}

data class ApiConfiguration(
    val rateLimitQueue: RateLimitQueue = RateLimitQueue()
)

/**
 * Options of the HTTP client, the defaults come from the Rust implementation
 */
fun restOptions(
    timeout: Duration = defaultRestOptions().timeoutMs.toLong().milliseconds,
    rejectUnauthorized: Boolean = defaultRestOptions().rejectUnauthorized,
    host: String = defaultRestOptions().host,
) = RestOptions(
    timeoutMs = timeout.inWholeMilliseconds.toULong(),
    rejectUnauthorized = rejectUnauthorized,
    host = host,
)
