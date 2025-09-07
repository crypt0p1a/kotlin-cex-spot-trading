package eu.codlab.cex.spot.trading.utils

import eu.codlab.http.Configuration
import eu.codlab.http.createClient
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

object ClientProvider {
    fun createClient(timeout: Long, enableLogs: Boolean) = createClient(
        configuration = Configuration(
            connectTimeoutMillis = timeout,
            socketTimeoutMillis = timeout,
            requestTimeoutMillis = timeout,
            enableLogs = enableLogs
        ),
        onAuth = {
            // nothing
        },
        onRequest = {
            it.header(HttpHeaders.ContentType, "application/json")
            it.header(HttpHeaders.UserAgent, "CEX.IO Spot Trading Kotlin Client")
        }
    )
}
