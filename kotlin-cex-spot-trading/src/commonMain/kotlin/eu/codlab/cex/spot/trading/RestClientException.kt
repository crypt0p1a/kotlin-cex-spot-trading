package eu.codlab.cex.spot.trading

import io.ktor.http.HttpStatusCode

class RestClientException(
    val statusCode: HttpStatusCode,
    val body: String
) : Throwable()
