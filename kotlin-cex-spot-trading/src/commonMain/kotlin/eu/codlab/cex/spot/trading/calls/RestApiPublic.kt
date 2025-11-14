package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.rest.RestOptions
import kotlinx.serialization.KSerializer

class RestApiPublic(
    options: RestOptions = RestOptions(),
    apiConfiguration: ApiConfiguration = ApiConfiguration(),
) : IRestApi {
    private val actualApi = RestApi(
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
