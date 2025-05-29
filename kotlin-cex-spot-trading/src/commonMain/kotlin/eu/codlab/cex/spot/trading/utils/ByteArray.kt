package eu.codlab.cex.spot.trading.utils

@OptIn(ExperimentalUnsignedTypes::class)
internal fun ByteArray.toHexString() = asUByteArray()
    .joinToString("") {
        @Suppress("MagicNumber")
        it.toString(16).padStart(2, '0')
    }
