package eu.codlab.cex.spot.trading.rest

data class Answer<T>(
    val ok: String,
    val data: T
)
