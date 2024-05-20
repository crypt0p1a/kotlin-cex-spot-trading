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
import io.ktor.util.date.getTimeMillis
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.kotlincrypto.macs.hmac.sha2.HmacSHA256

class RestClient(
    val clientId: String,
    val apiKey: String,
    val apiSecret: String,
    options: RestOptions = RestOptions()
) {
    val json = Json {
        encodeDefaults = true
    }

    val options: RestOptions = options.copy()
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

    suspend inline fun <reified I, reified O> callOld(
        action: String,
        isPublic: Boolean,
        params: I,
        serializer: KSerializer<I>
    ): O {
        val url = options.host
        val method = if (isPublic) {
            Method.GET
        } else {
            Method.POST
        }

        val nonce = getTimeMillis()
        val message = nonce.toString() + clientId + apiKey

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
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(params)
            }
        }

        if (response.status != HttpStatusCode.OK) {
            val text = response.bodyAsText()
            throw RestClientException(response.status, text)
        }

        return response.body()
    }

    private inline fun signature(message: String): String {
        if (null == apiSecret) return ""
        val hmac = HmacSHA256(apiSecret.toByteArray())
        hmac.update(message.toByteArray())

        val result = hmac.doFinal()
        return result.toHexString()
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }
