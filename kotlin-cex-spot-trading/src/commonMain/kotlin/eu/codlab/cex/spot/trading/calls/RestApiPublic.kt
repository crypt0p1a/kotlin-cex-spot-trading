package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.RestOptions
import io.ktor.client.request.get
import kotlinx.serialization.KSerializer

class RestApiPublic(options: RestOptions = RestOptions()) : InternalRestClient(options) {
    suspend fun <O> call(
        action: String,
        deserializer: KSerializer<O>
    ): O? {
        val url = options.host

        val response = client.get("$url$action")

        return map(response, deserializer)
    }
}
