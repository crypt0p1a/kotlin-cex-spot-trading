package eu.codlab.cex

import eu.codlab.cex.spot.trading.PrivateApi
import eu.codlab.cex.spot.trading.groups.funds.DepositToOrWithdrawalFromWalletRequest
import korlibs.time.DateTime
import kotlinx.coroutines.runBlocking

fun PrivateApi.moveFromCEXWallet(
    to: String,
    currency: String,
    amount: Double
) = runBlocking {
    val result = fundsDepositFromWallet(
        DepositToOrWithdrawalFromWalletRequest(
            clientTxId = "cli_deposit_${DateTime.nowUnixMillisLong()}",
            accountId = to,
            currency = currency,
            amount = "$amount"
        )
    )

    println("moved $amount $currency from $to to CEX.IO Wallet")
    println(result)
}
