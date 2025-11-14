package eu.codlab.cex.spot.trading.calls

import eu.codlab.cex.spot.trading.utils.Queue
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
    private val apiTokenPoolMaximum: UInt = 20U,
    private val apiTokenCreationInterval: Duration = 2.seconds,

    private val logger: (tag: String, msg: String) -> Unit = { _, _ -> },
) {
    companion object {
        private const val PREFIX = "[RateLimitQueue]"
    }

    private val queue = Queue(coroutineScope)
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

        block()
    }
}
