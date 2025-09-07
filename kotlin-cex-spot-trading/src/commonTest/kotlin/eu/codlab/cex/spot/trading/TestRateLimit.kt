package eu.codlab.cex.spot.trading

import eu.codlab.cex.spot.trading.calls.RateLimitQueue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

class TestRateLimit {
    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalAtomicApi::class)
    @Test
    fun `test creation of the rate limiter`() = runTest {
        val rateLimitQueue = RateLimitQueue()

        val done = AtomicInt(0)
        val max = 600
        val job = async {
            (0..<max).forEach { int ->
                async {
                    rateLimitQueue.enqueue {
                        done.incrementAndFetch()
                    }
                }
            }
        }

        while (done.load() != max) {
            advanceTimeBy(100.milliseconds)
        }

        job.await()
    }
}