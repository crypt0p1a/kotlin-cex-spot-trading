package eu.codlab.cex.spot.trading.groups.history.trades

import kotlinx.serialization.Serializable

@Serializable
data class TradeHistory(
    /**
     * Maximum number of trades, which can be returned in response.
     */
    val pageSize: Int,
    /**
     * An array containing data as to each trade event which satisfies requested criteria. If request is successful, this field should be present in response. It might be an empty array ([]). If this array is empty, then it means there are no trades, which satisfy Client’s request criteria. If there are trades, which satisfy requested criteria, then the elements in array are sorted by “dateISO” value in ascending order (from older to newer, considering “fromDateISO” / “toDateISO” or “fromTradeId” / “toTradeId” if indicated in request). If a few trade events occurred at the same moment of time, then such trade events are sorted additionally by “tradeId” value from lowest to higher sequence number (e.g. first “1677696747571-0”, then “1677696747571-1” etc.)
     */
    val trades: List<TradeFromHistory>
)

@Serializable
data class TradeFromHistory(
    /**
     * Side of trade event.
     */
    val side: OrderTypeHistory,
    /**
     * Date & Time of trade event in ISO format. (YYYY-MM-DDTHH:mm:ss.SSSZ).
     */
    val dateISO: String,
    /**
     * Trade execution price.
     */
    val price: Double,
    /**
     * Amount of trade in base currency.
     */
    val amount: Double,
    /**
     * Unique trade identifier in CEX.IO Spot Trading system. The value in this field should consist of trade UTC timestamp in milliseconds and sequence number separated by ”-” symbol (e.g. “1677696747571-0”). If several trades occurred at the same moment of time, then they differ from each other by incremented sequence number, starting from 0 (e.g. “1677696747571-0”, “1677696747571-1”, “1677696747571-2” etc.).
     */
    val tradeId: String
)