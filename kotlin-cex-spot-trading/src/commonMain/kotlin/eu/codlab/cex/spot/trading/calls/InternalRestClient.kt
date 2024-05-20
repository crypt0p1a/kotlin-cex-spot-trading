package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.JsonElementArray
import eu.codlab.cex.spot.trading.rest.JsonElementNull
import eu.codlab.cex.spot.trading.rest.JsonElementObject
import eu.codlab.cex.spot.trading.rest.JsonElementPrimitive
import eu.codlab.cex.spot.trading.rest.JsonElementWrapper
import eu.codlab.cex.spot.trading.rest.RestClientNetworkException
import eu.codlab.cex.spot.trading.rest.RestClientPairException
import eu.codlab.cex.spot.trading.rest.RestOptions
import eu.codlab.cex.spot.trading.rest.jsonString
import eu.codlab.cex.spot.trading.utils.ClientProvider
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract class InternalRestClient(options: RestOptions = RestOptions()) {
    protected val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    protected val options: RestOptions = options.copy()
    protected val client = ClientProvider.createClient(options.timeout)

    protected suspend fun <O> map(
        response: HttpResponse,
        deserializer: KSerializer<O>
    ): O? {
        if (response.status != HttpStatusCode.OK) {
            val text = response.bodyAsText()
            throw RestClientNetworkException(response.status, text)
        }

        val result = json.parseToJsonElement(response.bodyAsText())

        return when (val mapped = JsonElementWrapper.to(result)) {
            is JsonElementArray -> {
                return json.decodeFromJsonElement(deserializer, mapped.value)
            }

            is JsonElementNull -> null
            is JsonElementObject -> {
                val obj = mapped.value

                if (obj.containsKey("ok") && obj.jsonString("ok") == "ok") {
                    val data = obj["data"] ?: throw RestClientPairException(response.status, obj)

                    json.decodeFromJsonElement(deserializer, data)
                } else {
                    json.decodeFromJsonElement(deserializer, mapped.value)
                }
            }

            is JsonElementPrimitive -> {
                return json.decodeFromJsonElement(deserializer, mapped.value)
            }
        }
    }
}
