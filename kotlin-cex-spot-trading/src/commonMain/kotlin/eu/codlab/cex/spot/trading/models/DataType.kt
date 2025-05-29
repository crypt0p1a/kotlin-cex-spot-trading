package eu.codlab.cex.spot.trading.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DataType {
    @SerialName("bestAsk")
    BestAsk,

    @SerialName("bestBid")
    BestBid
}
