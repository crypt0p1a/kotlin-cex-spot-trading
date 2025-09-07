package eu.codlab.cex.spot.trading.rest

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.JsonElement

sealed class RestClientException(
    val statusCode: HttpStatusCode,
    message: String,
) : Throwable(message)

class RestClientNetworkException(
    statusCode: HttpStatusCode,
    val body: String,
) : RestClientException(statusCode, body)

class RestClientPairException(
    statusCode: HttpStatusCode,
    val body: JsonElement
) : RestClientException(statusCode, body.toString())
