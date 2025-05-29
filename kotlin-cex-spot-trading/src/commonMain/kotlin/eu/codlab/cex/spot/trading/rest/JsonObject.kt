package eu.codlab.cex.spot.trading.rest

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

internal fun JsonObject.jsonString(key: String): String? {
    val value = get(key)?.jsonPrimitive ?: return null

    return if (value.isString) {
        value.content
    } else {
        null
    }
}
