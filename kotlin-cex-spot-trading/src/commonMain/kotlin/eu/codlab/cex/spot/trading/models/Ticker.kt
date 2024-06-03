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
    val bestBid: Double,
    val bestAsk: Double,
    val bestBidChange: Double,
    val bestBidChangePercentage: PercentageFloat,
    val bestAskChange: Double,
    val bestAskChangePercentage: PercentageFloat,
    val volume30d: Double,
    val low: Double,
    val high: Double,
    val volume: Double,
    val quoteVolume: Double,
    val lastTradeVolume: Double,
    val last: Double,
    val lastTradePrice: Double,
    val priceChange: Double,
    val priceChangePercentage: Double,
    val lastTradeDateISO: String,
    val volumeUSD: Double
)
