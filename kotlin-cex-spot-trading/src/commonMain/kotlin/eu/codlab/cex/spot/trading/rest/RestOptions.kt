package eu.codlab.cex.spot.trading.rest

import kotlinx.serialization.Serializable

@Serializable
data class RestOptions(
    val apiLimit: Int = 300,
    val timeout: Long = 30000,
    val enableLogs: Boolean = false,
    val rejectUnauthorized: Boolean = true,
    val host: String = "https://trade.cex.io/api/spot",
    val log: (tag: String, text: String) -> Unit = { _, _ -> }
)
