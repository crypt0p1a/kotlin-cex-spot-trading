package eu.codlab.cex.spot.trading.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@OptIn(DelicateCoroutinesApi::class)
class Queue(private val scope: CoroutineScope = GlobalScope) {
    private val internalQueue = Channel<Job>(Channel.UNLIMITED)

    init {
        scope.async {
            for (job in internalQueue) {
                try {
                    job.join()
                } catch (err: Throwable) {
                    // ignored
                    err.printStackTrace()
                }
            }
        }
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun <T> enqueue(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> T
    ) = suspendCoroutine { continuation ->
        post(context) {
            try {
                continuation.resume(block())
            } catch (err: Throwable) {
                continuation.resumeWithException(err)
            }
        }
    }

    fun post(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) = internalQueue.trySend(
        scope.launch(
            context,
            CoroutineStart.LAZY,
            block
        )
    )

    fun cancel() {
        internalQueue.cancel()
        scope.cancel()
    }
}
