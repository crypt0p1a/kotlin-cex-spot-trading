package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.RestOptions
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.jsonObject

class RestApi(
    private val subEndpoint: PossibleRestSubEndpoint,
    options: RestOptions = RestOptions(),
    private val hijackRequest: (HttpRequestBuilder.(action: String, body: String) -> Unit)? = null
) : InternalRestClient(options),
    IRestApi {
    private val rateLimitQueue = RateLimitQueue()

    override suspend fun <O> call(
        action: String,
        deserializer: KSerializer<O>
    ): O = call(action, null, Unit.serializer(), deserializer)

    override suspend fun <I, O> call(
        action: String,
        params: I?,
        serializer: KSerializer<I>,
        deserializer: KSerializer<O>
    ): O {
        val json = if (null != params) {
            json.encodeToJsonElement(serializer, params).jsonObject
        } else {
            json.parseToJsonElement("{}")
        }

        val url = options.host
        val response = rateLimitQueue.enqueue {
            client.post("$url/${subEndpoint.endpoint}/$action") {
                contentType(ContentType.Application.Json)

                json.toString().let {
                    hijackRequest?.invoke(this, action, it)
                    setBody(it)
                }
            }
        }

        return map(response, deserializer)
    }

    override fun close() = client.close()
}
