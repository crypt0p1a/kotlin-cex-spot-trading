package eu.codlab.cex.spot.trading

import eu.codlab.configuration.Configuration
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class TestPrivateApi {
    val api = PrivateApi(
        Configuration.clientId,
        Configuration.apiKey,
        Configuration.apiSecret
    )

    @Test
    fun testBalance() = runTest {
        println(api.accountBalance())
    }

    @Test
    fun testActiveOrders() = runTest {
        println(api.activeOrdersStatus())
    }
}
