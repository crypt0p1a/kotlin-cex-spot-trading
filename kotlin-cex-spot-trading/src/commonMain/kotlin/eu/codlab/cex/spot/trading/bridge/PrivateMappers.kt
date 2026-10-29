package eu.codlab.cex.spot.trading.bridge

import eu.codlab.cex.spot.trading.groups.account.balance.AccountBalance
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusRequest
import eu.codlab.cex.spot.trading.groups.account.balance.AccountStatusResult
import eu.codlab.cex.spot.trading.groups.account.balance.BalanceForAccount
import eu.codlab.cex.spot.trading.groups.account.create.CreateAccountResult
import eu.codlab.cex.spot.trading.groups.account.fees.TradingFeeBalancesForAccount
import eu.codlab.cex.spot.trading.groups.fees.FeeStrategy
import eu.codlab.cex.spot.trading.groups.fees.FeeVolume
import eu.codlab.cex.spot.trading.groups.fees.StrategyConfig
import eu.codlab.cex.spot.trading.groups.volume.Volume
import eu.codlab.cex.spot.trading.models.TradingFee
import eu.codlab.cex.spot.trading.models.TradingFees
import eu.codlab.cex.spot.trading.rust.bindings.AccountBalance as RustAccountBalance
import eu.codlab.cex.spot.trading.rust.bindings.AccountStatusRequest as RustAccountStatusRequest
import eu.codlab.cex.spot.trading.rust.bindings.AccountStatusResult as RustAccountStatusResult
import eu.codlab.cex.spot.trading.rust.bindings.BalanceForAccount as RustBalanceForAccount
import eu.codlab.cex.spot.trading.rust.bindings.CreateAccountResult as RustCreateAccountResult
import eu.codlab.cex.spot.trading.rust.bindings.FeeStrategy as RustFeeStrategy
import eu.codlab.cex.spot.trading.rust.bindings.FeeVolume as RustFeeVolume
import eu.codlab.cex.spot.trading.rust.bindings.StrategyConfig as RustStrategyConfig
import eu.codlab.cex.spot.trading.rust.bindings.TradingFee as RustTradingFee
import eu.codlab.cex.spot.trading.rust.bindings.TradingFeeBalancesForAccount as RustTradingFeeBalancesForAccount
import eu.codlab.cex.spot.trading.rust.bindings.TradingFees as RustTradingFees
import eu.codlab.cex.spot.trading.rust.bindings.Volume as RustVolume

internal fun RustTradingFees.toKotlin() = TradingFees(
    tradingFee = tradingFee.mapValues { it.value.toKotlin() },
)

internal fun RustTradingFee.toKotlin() = TradingFee(
    percent = percent,
)

internal fun RustFeeStrategy.toKotlin() = FeeStrategy(
    strategyConfig = strategyConfig.toKotlin(),
)

internal fun RustStrategyConfig.toKotlin() = StrategyConfig(
    default = defaultFees.map { it.toKotlin() },
)

internal fun RustFeeVolume.toKotlin() = FeeVolume(
    fee = fee,
    volume = volume.toInt(),
)

internal fun RustVolume.toKotlin() = Volume(
    period = period,
    volume = volume,
    currency = currency,
)

internal fun RustCreateAccountResult.toKotlin() = CreateAccountResult(
    accountId = accountId,
)

internal fun AccountStatusRequest.toRust() = RustAccountStatusRequest(
    currencies = currencies,
    accountIds = accountIds?.toList(),
)

internal fun RustAccountStatusResult.toKotlin() = AccountStatusResult(
    convertedCurrency = convertedCurrency,
    balancesPerAccounts = balancesPerAccounts.mapValues { account ->
        account.value.mapValues { it.value.toKotlin() }
    },
    tradingFeeBalancesPerAccounts = tradingFeeBalancesPerAccounts?.mapValues { account ->
        account.value.mapValues { it.value.toKotlin() }
    },
)

internal fun RustBalanceForAccount.toKotlin() = BalanceForAccount(
    balance = balance,
    balanceOnHold = balanceOnHold,
    balanceInConvertedCurrency = balanceInConvertedCurrency,
)

internal fun RustTradingFeeBalancesForAccount.toKotlin() = TradingFeeBalancesForAccount(
    balance = balance,
    expirationDate = expirationDate,
)

internal fun RustAccountBalance.toKotlin() = AccountBalance(
    balance = balance,
)
