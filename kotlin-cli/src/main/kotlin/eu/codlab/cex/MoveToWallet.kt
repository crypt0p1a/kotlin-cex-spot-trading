package eu.codlab.cex

import eu.codlab.cex.spot.trading.PrivateApi
import eu.codlab.cex.spot.trading.groups.funds.DepositToOrWithdrawalFromWalletRequest
import korlibs.time.DateTime
import kotlinx.coroutines.runBlocking

fun PrivateApi.moveToCEXWallet(
    to: String,
    currency: String,
    amount: Double
) = runBlocking {
    val result = fundsWithdrawToWallet(
        DepositToOrWithdrawalFromWalletRequest(
            clientTxId = "cli_withdraw_${DateTime.nowUnixMillisLong()}",
            accountId = to,
            currency = currency,
            amount = "$amount"
        )
    )

    println("moved $amount $currency from CEX.IO Wallet to $to")
    println(result)
}
