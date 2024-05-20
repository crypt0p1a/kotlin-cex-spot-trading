package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

private object PriceAmountSerializer : KSerializer<List<PriceAmount>> {

    override val descriptor = PrimitiveSerialDescriptor(
        "PriceAmount",
        PrimitiveKind.FLOAT
    )

    override fun deserialize(decoder: Decoder): List<PriceAmount> {
        val result = decoder.decodeSerializableValue(
            ListSerializer(
                ListSerializer(
                    Double.serializer()
                )
            )
        )

        return result.map {
            PriceAmount(
                it.first(),
                it.last()
            )
        }
    }

    override fun serialize(encoder: Encoder, value: List<PriceAmount>) =
        encoder.encodeSerializableValue(
            ListSerializer(
                ListSerializer(
                    Double.serializer()
                )
            ),
            value.map {
                listOf(it.amount, it.price)
            }
        )
}

@Serializable
data class OrderBook(
    val timestamp: Long,
    @SerialName("timestamp_ms")
    val timestampMs: Long,
    @Serializable(with = PriceAmountSerializer::class)
    val bids: List<PriceAmount>,
    @Serializable(with = PriceAmountSerializer::class)
    val asks: List<PriceAmount>,
    val pair: String,
    val id: Long,
    @SerialName("sell_total")
    val sellTotal: Double,
    @SerialName("buy_total")
    val buyTotal: Double,
)

@Serializable
data class PriceAmount(
    val price: Double,
    val amount: Double
)