package eu.codlab.cex.spot.trading.calls

import kotlinx.coroutines.delay
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch
import kotlin.concurrent.atomics.minusAssign
import kotlin.time.Duration.Companion.seconds

class RateLimitQueue {
    @OptIn(ExperimentalAtomicApi::class)
    private val atomic = AtomicInt(0)

    @OptIn(ExperimentalAtomicApi::class)
    suspend fun <T> enqueue(block: suspend () -> T): T {
        atomic.incrementAndFetch()

        while (atomic.load() >= 600) {
            delay(2.seconds)
        }

        return try {
            block().let {
                atomic.minusAssign(1)
                it
            }
        } catch (err: Throwable) {
            atomic.minusAssign(1)
            throw err
        }
    }
}
