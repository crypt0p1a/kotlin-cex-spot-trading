package eu.codlab.cex.spot.trading.calls

import kotlinx.serialization.KSerializer

interface IRestApi {
    suspend fun <O> call(
        action: String,
        deserializer: KSerializer<O>
    ): O?

    suspend fun <I, O> call(
        action: String,
        params: I? = null,
        serializer: KSerializer<I>,
        deserializer: KSerializer<O>
    ): O?
}
