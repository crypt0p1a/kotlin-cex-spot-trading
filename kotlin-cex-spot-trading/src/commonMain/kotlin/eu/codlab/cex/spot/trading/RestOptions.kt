package eu.codlab.cex.spot.trading

import kotlinx.serialization.Serializable

@Serializable
data class RestOptions(
    val apiLimit: Int = 300,
    val timeout: Long = 30000,
    val rejectUnauthorized: Boolean = true,
    val host: String = "https://trace.cex.io/api",
    val apiUrlPublic: String = "https://trade.cex.io/api/spot/rest-public/",
    val apiUrl: String = "https://trade.cex.io/api/spot/rest/",
    val log: (tag: String, text: String) -> Unit = { _, _ -> }
)
