package eu.codlab.cex.spot.trading.groups.orderbook

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object PriceAmountSerializer : KSerializer<List<PriceAmount>> {
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
