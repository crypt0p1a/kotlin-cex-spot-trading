package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.RestOptions
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.util.date.getTimeMillis
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.KSerializer
import org.kotlincrypto.macs.hmac.sha2.HmacSHA256
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.math.round

class RestApiSecret(
    coroutineScope: CoroutineScope,
    private val apiKey: String,
    private val apiSecret: String,
    options: RestOptions = RestOptions(),
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
) : IRestApi {
    private val millisInSecond = 1000

    private val actualApi = RestApi(
        coroutineScope,
        PossibleRestSubEndpoint.Private,
        apiConfiguration,
        options
    ) { action, body ->
        val nonce = round(getTimeMillis() * 1.0 / millisInSecond).toLong()
        val message = action + nonce + body
        val signature = signature(message)

        headers {
            set("X-AGGR-KEY", apiKey)
            set("X-AGGR-TIMESTAMP", "$nonce")
            set("X-AGGR-SIGNATURE", signature)
        }

        setBody(body)
    }

    override suspend fun <O> call(
        action: String,
        deserializer: KSerializer<O>
    ) = actualApi.call(action, deserializer)

    override suspend fun <I, O> call(
        action: String,
        params: I?,
        serializer: KSerializer<I>,
        deserializer: KSerializer<O>
    ) = actualApi.call(action, params, serializer, deserializer)

    override fun close() = actualApi.close()

    @OptIn(ExperimentalEncodingApi::class)
    private fun signature(message: String): String {
        val hmac = HmacSHA256(apiSecret.toByteArray())
        hmac.update(message.toByteArray())

        val result = hmac.doFinal()
        return Base64.encode(result)
    }
}
