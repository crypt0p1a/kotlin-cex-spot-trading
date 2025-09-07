package eu.codlab.cex.spot.trading.rest

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

sealed class JsonElementWrapper {
    companion object {
        fun to(jsonElement: JsonElement): JsonElementWrapper {
            val callbacks: List<() -> JsonElementWrapper> = listOf(
                { JsonElementObject(jsonElement.jsonObject) },
                { JsonElementPrimitive(jsonElement.jsonPrimitive) },
                { JsonElementArray(jsonElement.jsonArray) },
                { JsonElementNull },
            )

            @Suppress("TooGenericExceptionCaught", "SwallowedException")
            callbacks.forEach {
                try {
                    return it()
                } catch (err: Throwable) {
                    // skip
                }
            }

            return JsonElementNull
        }
    }
}

class JsonElementArray(val value: JsonArray) : JsonElementWrapper()

class JsonElementObject(val value: JsonObject) : JsonElementWrapper()

data object JsonElementNull : JsonElementWrapper()

class JsonElementPrimitive(val value: JsonPrimitive) : JsonElementWrapper()
