package eu.codlab.cex

import eu.codlab.cex.spot.trading.PrivateApi
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusRequest
import kotlinx.coroutines.runBlocking

fun PrivateApi.listWallets() = runBlocking {
    println("listing the wallets")

    val result = getMyAccountStatus(AccountStatusRequest())

    val output = "default currency: ${result.convertedCurrency}\n" +
            result.balancesPerAccounts.keys.joinToString("\n") {
                "Account : ${it}\n" +
                        result.balancesPerAccounts[it]!!.let { holder ->
                            holder.keys.joinToString("\n") { crypto ->
                                "  $crypto := balance/${holder[crypto]!!.balance} " +
                                        "on hold/${holder[crypto]!!.balanceOnHold} -> " +
                                        "converted ${holder[crypto]!!.balanceInConvertedCurrency}"
                            }
                        }
            }
    println(output)
}
