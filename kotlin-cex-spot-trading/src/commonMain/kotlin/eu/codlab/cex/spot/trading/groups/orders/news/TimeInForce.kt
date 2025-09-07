package eu.codlab.cex.spot.trading.groups.orders.news

import kotlinx.serialization.Serializable

/**
 * When Client indicates TimeInForce value in the request, he instructs CEX.IO Spot Trading on how
 * long the order should be open after initial placement or execution. TimeInForce should be
 * indicated for all types of orders (Limit, Stop Limit, Market).
 */
@Serializable
enum class TimeInForce {
    /**
     * Good Till Cancel (GTC) — the order remains open till either full execution or cancellation.
     * GTC orders can be executed partially.
     */
    GTC,

    /**
     * Immediate or Cancel (IOC) — the order may be immediately executed (fully or partially) or not
     * immediately executed. An outstanding amount after immediate execution will be cancelled.
     */
    IOC,

    /**
     * Good Till Date (GTD) — the order remains open till it is either fully executed, or cancelled,
     * or till the time reaches the moment specified in ExpireTime. GTD orders can be partially
     * executed.
     */
    GTD
}