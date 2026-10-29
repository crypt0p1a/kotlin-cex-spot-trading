package eu.codlab.cex.spot.trading.rest

import kotlinx.serialization.json.JsonElement

/**
 * [statusCode] is the HTTP status of the answer of CEX.IO
 */
sealed class RestClientException(
    val statusCode: Int,
    message: String,
) : Throwable(message)

class RestClientNetworkException(
    statusCode: Int,
    val body: String,
) : RestClientException(statusCode, body)

class RestClientPairException(
    statusCode: Int,
    val body: JsonElement
) : RestClientException(statusCode, body.toString())
