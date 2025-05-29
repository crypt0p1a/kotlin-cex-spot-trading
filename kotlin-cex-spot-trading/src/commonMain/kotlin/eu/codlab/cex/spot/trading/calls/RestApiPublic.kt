package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.RestOptions
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.KSerializer

class RestApiPublic(options: RestOptions = RestOptions()) :
    InternalRestClient(options),
    IRestApi {
    override suspend fun <O> call(
        action: String,
        deserializer: KSerializer<O>
    ): O? {
        val url = options.host

        val response = client.get("$url/rest-public/$action")

        return map(response, deserializer)
    }

    override suspend fun <I, O> call(
        action: String,
        params: I?,
        serializer: KSerializer<I>,
        deserializer: KSerializer<O>
    ): O? {
        val url = options.host

        val mapped = json.encodeToJsonElement(serializer, params)

        val response = client.post("$url/rest-public/$action") {
            setBody(mapped)
        }

        return map(response, deserializer)
    }
}
