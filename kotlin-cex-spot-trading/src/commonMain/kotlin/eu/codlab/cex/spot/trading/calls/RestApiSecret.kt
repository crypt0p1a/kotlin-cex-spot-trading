package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.RestOptions
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.date.getTimeMillis
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.jsonObject
import org.kotlincrypto.macs.hmac.sha2.HmacSHA256

class RestApiSecret(
    private val clientId: String,
    private val apiKey: String,
    private val apiSecret: String,
    options: RestOptions = RestOptions()
) : InternalRestClient(options) {

    suspend fun <I, O> call(
        action: String,
        params: I? = null,
        serializer: KSerializer<I>,
        deserializer: KSerializer<O>
    ): O? {
        val url = options.host
        val nonce = getTimeMillis()
        val message = nonce.toString() + clientId + apiKey
        val signature = signature(message)

        val response = client.post("$url$action") {
            contentType(ContentType.Application.FormUrlEncoded)

            val addons = mapOf(
                "key" to apiKey,
                "signature" to signature,
                "nonce" to nonce
            )

            if (null != params) {
                setBody(
                    json.encodeToJsonElement(serializer, params).jsonObject.toMap() + addons
                )
            } else {
                setBody(addons)
            }
        }

        return map(response, deserializer)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun signature(message: String): String {
        val hmac = HmacSHA256(apiSecret.toByteArray())
        hmac.update(message.toByteArray())

        val result = hmac.doFinal()
        return result.toHexString()
    }
}
