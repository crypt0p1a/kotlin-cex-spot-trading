package eu.codlab.cex.spot.trading.utils

@OptIn(ExperimentalUnsignedTypes::class)
internal fun ByteArray.toHexString() = asUByteArray()
    .joinToString("") {
        it.toString(16).padStart(2, '0')
    }
