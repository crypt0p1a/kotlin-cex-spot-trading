package eu.codlab.cex.spot.trading.groups.pairsinfo

import kotlinx.serialization.Serializable

/**
 * List of supported trading pairs for which Client wants to receive configuration parameters.
 * Trading pair should contain two currencies in uppercase divided by “-“ symbol. Pair should be
 * listed in traditional direction. For example “BTC-USD”, but not “USD-BTC”. If this field is
 * present in the request, then at least 1 pair should be indicated in an array. If this field is
 * absent, then it means Client requests configuration parameters for all supported pairs.
 */
@Serializable
data class Pairs(
    /**
     * Currency pair, for which Client wants to receive his fee. Pair should contain two currencies
     * in upper case divided by "-" symbol. Pair should be listed in traditional direction. For
     * example, "BTC-USD", but not "USD-BTC". If this field is missing, or if it contains empty
     * string (""), or null, then it means Client wants to receive fee for all pairs.
     */
    val pairs: List<String>
)
