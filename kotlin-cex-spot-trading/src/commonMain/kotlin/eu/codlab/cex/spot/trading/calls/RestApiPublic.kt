package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.RestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.KSerializer
import kotlin.time.Duration.Companion.minutes

class RestApiPublic(
    coroutineScope: CoroutineScope,
    options: RestOptions = RestOptions(),
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
) : IRestApi {
    private val actualApi = RestApi(
        coroutineScope,
        PossibleRestSubEndpoint.Public,
        apiConfiguration,
        options
    )

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
}
