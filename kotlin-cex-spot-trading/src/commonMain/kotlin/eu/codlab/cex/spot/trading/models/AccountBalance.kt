package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlinx.serialization.json.put


private object AccountBalanceSerializer : KSerializer<AccountBalance> {
    private val json = Json

    override val descriptor = PrimitiveSerialDescriptor(
        "PriceAmount",
        PrimitiveKind.FLOAT
    )

    override fun deserialize(decoder: Decoder): AccountBalance {
        val jsonDecoder = decoder as JsonDecoder

        val result = jsonDecoder.decodeJsonElement().jsonObject
        val symbols: MutableMap<String, Balance> = mutableMapOf()

        result.keys.forEach { key ->
            if (key == "timestamp" || key == "username") return@forEach
            val obj = result[key] ?: return@forEach

            symbols[key] = json.decodeFromJsonElement(obj)
        }

        return AccountBalance(
            timestamp = result["timestamp"]?.jsonPrimitive?.long ?: 0,
            username = result["username"]?.jsonPrimitive?.content ?: "",
            symbols = symbols
        )
    }

    //This will need to be properly implemented
    override fun serialize(encoder: Encoder, value: AccountBalance) {
        val encoderJson = encoder as JsonEncoder
        encoderJson.encodeJsonElement(
            buildJsonObject {
                put("timestamp", value.timestamp)
                put("username", value.username)
                value.symbols.entries.forEach { (key, value) ->
                    put(key, json.encodeToJsonElement(value))
                }
            }
        )
    }
}

@Serializable(with = AccountBalanceSerializer::class)
data class AccountBalance(
    val timestamp: Long,
    val username: String,
    val symbols: Map<String, Balance>
)

@Serializable
data class Balance(
    val available: Double,
    val orders: Double
)
