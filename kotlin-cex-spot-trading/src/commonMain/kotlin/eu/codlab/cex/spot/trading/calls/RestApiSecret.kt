package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.RestOptions
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.date.getTimeMillis
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.jsonObject
import org.kotlincrypto.macs.hmac.sha2.HmacSHA256
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.math.round

class RestApiSecret(
    private val clientId: String,
    private val apiKey: String,
    private val apiSecret: String,
    options: RestOptions = RestOptions()
) : InternalRestClient(options) {

    suspend fun <O> call(
        action: String,
        deserializer: KSerializer<O>
    ): O? = call(action, null, Unit.serializer(), deserializer)

    suspend fun <I, O> call(
        action: String,
        params: I? = null,
        serializer: KSerializer<I>,
        deserializer: KSerializer<O>
    ): O? {
        val json = if (null != params) {
            json.encodeToJsonElement(serializer, params).jsonObject
        } else {
            json.parseToJsonElement("{}")
        }

        val url = options.host
        val nonce = round(getTimeMillis() * 1.0 / 1000).toLong()
        val message = action + nonce + json.toString()
        val signature = signature(message)

        val response = client.post("$url/rest/$action") {
            contentType(ContentType.Application.Json)

            headers {
                set("X-AGGR-KEY", apiKey)
                set("X-AGGR-TIMESTAMP", "$nonce")
                set("X-AGGR-SIGNATURE", signature)
            }

            setBody(json.toString())
        }

        return map(response, deserializer)
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun signature(message: String): String {
        val hmac = HmacSHA256(apiSecret.toByteArray())
        hmac.update(message.toByteArray())

        val result = hmac.doFinal()
        return Base64.encode(result)
    }
}
