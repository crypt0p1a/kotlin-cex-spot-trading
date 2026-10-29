package eu.codlab.cex.spot.trading.bridge

import eu.codlab.cex.spot.trading.rest.RestClientNetworkException
import eu.codlab.cex.spot.trading.rest.RestClientPairException
import eu.codlab.cex.spot.trading.rust.bindings.CexException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive

/**
 * Calls the Rust implementation, its errors are thrown as the ones of the Kotlin implementation:
 * [RestClientNetworkException], [RestClientPairException] and [SerializationException]. The
 * transport errors are thrown as [CexException.Transport]
 */
@Suppress("SwallowedException")
internal suspend fun <T> rustCall(block: suspend () -> T): T = try {
    block()
} catch (err: CexException.Network) {
    throw RestClientNetworkException(err.status.toInt(), err.body)
} catch (err: CexException.Api) {
    val body = try {
        Json.parseToJsonElement(err.body)
    } catch (_: SerializationException) {
        JsonPrimitive(err.body)
    }

    throw RestClientPairException(err.status.toInt(), body)
} catch (err: CexException.Decode) {
    throw SerializationException(err.reason)
}
