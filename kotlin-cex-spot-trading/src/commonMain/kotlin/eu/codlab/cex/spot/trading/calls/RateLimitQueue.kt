package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.utils.Queue
import eu.codlab.cex.spot.trading.rust.RateLimitQueue as RustRateLimitQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalAtomicApi::class, DelicateCoroutinesApi::class)
class RateLimitQueue(
    coroutineScope: CoroutineScope = GlobalScope,
    private val apiTokenPoolMaximum: UInt = 10U,
    private val apiTokenCreationInterval: Duration = 2.seconds,

    private val logger: (tag: String, msg: String) -> Unit = { _, _ -> },
) {
    companion object {
        private const val PREFIX = "[RateLimitQueue]"
    }

    private val queue = Queue(coroutineScope)

    /**
     * Rate limiter of the calls made by the Rust implementation. Until all the calls are migrated,
     * its tokens are counted separately from this queue's
     */
    internal val rust = RustRateLimitQueue(apiTokenPoolMaximum, apiTokenCreationInterval, logger)
    private var count: UInt = apiTokenPoolMaximum

    init {
        coroutineScope.launch {
            while (isActive) {
                count++

                if (count > apiTokenPoolMaximum) {
                    count = apiTokenPoolMaximum
                }

                logger(PREFIX, "pool is now $count")

                delay(apiTokenCreationInterval)
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T> enqueue(tag: String, block: suspend () -> T): T = queue.enqueue {
        /**
         * We normally expect API rate limit to hit after 10min and 600 calls
         * but we are going to artificially give some rest beforehand...
         */
        while (count == 0U) {
            logger(
                PREFIX,
                "$tag :: No token available (max:$apiTokenPoolMaximum)"
            )
            delay(2.seconds)
        }

        count--

        try {
            block()
        } catch (err: Throwable) {
            logger(PREFIX, "$tag :: Error: ${err.message} / tokens: $count")
            throw err
        }
    }
}
