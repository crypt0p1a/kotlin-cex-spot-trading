package eu.codlab.cex.spot.trading.rest

import eu.codlab.cex.spot.trading.rust.restOptions
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.milliseconds

/**
 * Options of the HTTP client, [timeout] in milliseconds. The pending and the failed calls are
 * logged by the logger of the [eu.codlab.cex.spot.trading.calls.RateLimitQueue]
 */
@Serializable
data class RestOptions(
    val timeout: Long = 30000,
    val rejectUnauthorized: Boolean = true,
    val host: String = "https://trade.cex.io/api/spot",
)

/**
 * Options of the HTTP client of the Rust implementation
 */
internal fun RestOptions.toRust() = restOptions(
    timeout = timeout.milliseconds,
    rejectUnauthorized = rejectUnauthorized,
    host = host,
)
