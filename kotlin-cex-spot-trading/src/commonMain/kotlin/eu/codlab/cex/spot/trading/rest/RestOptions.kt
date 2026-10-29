package eu.codlab.cex.spot.trading.rest

import eu.codlab.cex.spot.trading.rust.restOptions
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.milliseconds

@Serializable
data class RestOptions(
    val apiLimit: Int = 300,
    val timeout: Long = 30000,
    val enableLogs: Boolean = false,
    val rejectUnauthorized: Boolean = true,
    val host: String = "https://trade.cex.io/api/spot",
    val log: (tag: String, text: String) -> Unit = { _, _ -> }
)

/**
 * Options of the HTTP client of the Rust implementation, the logs are not forwarded
 */
internal fun RestOptions.toRust() = restOptions(
    timeout = timeout.milliseconds,
    rejectUnauthorized = rejectUnauthorized,
    host = host,
)
