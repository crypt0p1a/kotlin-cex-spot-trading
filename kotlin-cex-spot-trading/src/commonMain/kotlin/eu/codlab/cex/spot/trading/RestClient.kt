package eu.codlab.cex.spot.trading

import eu.codlab.http.Configuration
import eu.codlab.http.createClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

class RestClient(
    private val apiKey: String?,
    private val apiSecret: String?,
    options: RestOptions = RestOptions()
) {
    val json = Json {
        encodeDefaults = true
    }

    val options: RestOptions = options.copy()

    val isPublic = null != apiKey && null != apiSecret
    val client = createClient(
        configuration = Configuration(
            connectTimeoutMillis = options.timeout,
            socketTimeoutMillis = options.timeout,
            requestTimeoutMillis = options.timeout,
            enableLogs = true
        ),
        onAuth = {

        },
        onRequest = {
            it.header(HttpHeaders.ContentType, "application/json")
            it.header(HttpHeaders.UserAgent, "CEX.IO Spot Trading Kotlin Client")
        }
    )

    suspend inline fun <reified I, reified O> call(
        action: String,
        method: Method,
        params: I,
        serializer: KSerializer<I>
    ): O {
        val url = if (isPublic) {
            options.apiUrlPublic
        } else {
            options.apiUrl
        }

        val response = when (method) {
            Method.GET -> {
                val map = json.encodeToJsonElement(serializer, params).jsonObject.toMap()
                client.get("$url$action") {
                    url {
                        map.forEach { (key, value) -> parameters.append(key, value.toString()) }
                    }
                }
            }

            Method.POST -> client.post("$url$action") {
                contentType(ContentType.Application.Json)
                setBody(params)
            }
        }

        if (response.status != HttpStatusCode.OK) {
            val text = response.bodyAsText()
            throw RestClientException(response.status, text)
        }

        return response.body()
    }
}
