package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.RestOptions
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.date.getTimeMillis
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

class RestApiPublic(options: RestOptions = RestOptions()) : InternalRestClient(options) {
    suspend fun <O> call(
        action: String,
        deserializer: KSerializer<O>
    ): O? {
        val url = options.host

        val response = client.get("$url/rest-public/$action")

        return map(response, deserializer)
    }

    suspend fun <I, O> call(
        action: String,
        params: I,
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
