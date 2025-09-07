package eu.codlab.cex

import eu.codlab.cex.spot.trading.PrivateApi
import kotlinx.coroutines.runBlocking

fun PrivateApi.createNewWallet(name: String?, currency: String?) = runBlocking {
    val result = createAccount(name!!, currency!!)

    println("created the wallet named $name")
    println(result)
}
