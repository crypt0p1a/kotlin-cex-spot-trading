package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias PercentageFloat = @Serializable(with = PercentageFloatSerializer::class) Float

private object PercentageFloatSerializer : KSerializer<Float> {

    override val descriptor = PrimitiveSerialDescriptor(
        "kotlin.FloatFromString",
        PrimitiveKind.FLOAT
    )

    override fun deserialize(decoder: Decoder): Float =
        decoder.decodeString().toFloatOrNull() ?: 0.0f

    override fun serialize(encoder: Encoder, value: Float) =
        encoder.encodeString("$value")
}

@Serializable
data class Ticker(
    val timestamp: Long,
    val low: Double,
    val high: Double,
    val last: Double,
    val volume: Double,
    val volume30d: Double,
    val bid: Double = 0.0,
    val ask: Double = 0.0,
    val priceChange: Double,
    private val priceChangePercentage: PercentageFloat,
    val pair: String
)
