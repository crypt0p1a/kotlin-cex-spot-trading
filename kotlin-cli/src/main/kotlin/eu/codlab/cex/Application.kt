package eu.codlab.cex

import eu.codlab.cex.spot.trading.PrivateApi
import eu.codlab.cex.spot.trading.rest.RestOptions
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

@ExperimentalCli
fun main(args: Array<String>) {
    val config = Config()
    runBlocking { config.load() }

    val api = PrivateApi(
        config.get("cexApiKey")!!,
        config.get("cexApiSecret")!!,
        RestOptions(enableLogs = true)
    )

    val parser = ArgParser("CEX")

    class Wallets : Subcommand("wallets", "Wallets command") {
        val walletName by option(
            ArgType.String,
            fullName = "name",
            shortName = "n",
            "Name of the wallet"
        )
        val currency by option(
            ArgType.String,
            "currency",
            shortName = "c",
            "Expected currency name"
        )

        val actions = listOf(
            "create" to { api.createNewWallet(walletName, currency) },
            "list" to { api.listWallets() }
        )
        val action by argument(
            ArgType.String,
            "action",
            "Action to perform : ${actions.map { it.first }}"
        )

        override fun execute() {
            actions.find { it.first == action }?.second?.invoke()
        }
    }

    class Orders : Subcommand("orders", "Wallets command") {
        val walletName by option(
            ArgType.String,
            fullName = "name",
            shortName = "n",
            "Name of the wallet"
        )
        val orderId by option(
            ArgType.String,
            fullName = "orderId",
            shortName = "o",
            "Specific order id"
        )

        val actions = listOf(
            "list" to { api.showOrders(walletName, orderId) },
            "cancel" to { api.cancelOrder(orderId) }
        )
        val action by argument(
            ArgType.String,
            "action",
            "Action to perform : ${actions.map { it.first }}"
        )

        override fun execute() {
            actions.find { it.first == action }?.second?.invoke()
        }
    }

    class Move : Subcommand("move", "Move command") {
        val accountId by option(
            ArgType.String,
            fullName = "accountId",
            shortName = "a",
            "AccountId to withdraw from or deposit to"
        )
        val currency by option(
            ArgType.String,
            fullName = "currency",
            shortName = "c",
            "Currency symbol to withdraw from or deposit to. e.g. BTC"
        )
        val amount by option(
            ArgType.Double,
            fullName = "amount",
            shortName = "v",
            "Amount to move"
        )

        val actions = listOf(
            "withdraw" to {
                api.moveToCEXWallet(
                    to = accountId!!,
                    currency = currency!!,
                    amount = amount!!
                )
            },
            "deposit" to {
                api.moveFromCEXWallet(
                    to = accountId!!,
                    currency = currency!!,
                    amount = amount!!
                )
            }
        )
        val action by argument(
            ArgType.String,
            "action",
            "Action to perform : ${actions.map { it.first }}"
        )

        override fun execute() {
            actions.find { it.first == action }?.second?.invoke()
        }
    }

    parser.subcommands(Wallets(), Orders(), Move())

    parser.parse(args)

    exitProcess(0)
}
