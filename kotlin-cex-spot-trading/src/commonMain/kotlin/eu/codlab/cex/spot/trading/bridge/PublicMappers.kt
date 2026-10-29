package eu.codlab.cex.spot.trading.bridge

import eu.codlab.cex.spot.trading.groups.currency.CurrencyInfo
import eu.codlab.cex.spot.trading.groups.pairsinfo.PairInfo
import eu.codlab.cex.spot.trading.groups.processing.Blockchain
import eu.codlab.cex.spot.trading.groups.processing.Capability
import eu.codlab.cex.spot.trading.groups.processing.ProcessingInfo
import eu.codlab.cex.spot.trading.models.ServerTime
import eu.codlab.cex.spot.trading.rust.bindings.Blockchain as RustBlockchain
import eu.codlab.cex.spot.trading.rust.bindings.Capability as RustCapability
import eu.codlab.cex.spot.trading.rust.bindings.CurrencyInfo as RustCurrencyInfo
import eu.codlab.cex.spot.trading.rust.bindings.PairInfo as RustPairInfo
import eu.codlab.cex.spot.trading.rust.bindings.ProcessingInfo as RustProcessingInfo
import eu.codlab.cex.spot.trading.rust.bindings.ServerTime as RustServerTime

internal fun RustServerTime.toKotlin() = ServerTime(
    timestamp = timestamp,
    isoDate = isoDate,
)

internal fun RustCurrencyInfo.toKotlin() = CurrencyInfo(
    currency = currency,
    walletDeposit = walletDeposit,
    walletWithdrawal = walletWithdrawal,
    fiat = fiat,
    precision = precision,
    walletPrecision = walletPrecision,
)

internal fun RustPairInfo.toKotlin() = PairInfo(
    base = base,
    quote = quote,
    baseMin = baseMin,
    baseMax = baseMax,
    baseLotSize = baseLotSize,
    quoteMin = quoteMin,
    quoteMax = quoteMax,
    quoteLotSize = quoteLotSize,
    basePrecision = basePrecision,
    quotePrecision = quotePrecision,
    pricePrecision = pricePrecision,
    minPrice = minPrice,
    maxPrice = maxPrice,
)

internal fun RustProcessingInfo.toKotlin() = ProcessingInfo(
    name = name,
    blockchains = blockchains.mapValues { it.value.toKotlin() },
)

@Suppress("DEPRECATION")
internal fun RustBlockchain.toKotlin() = Blockchain(
    type = blockchainType,
    deposit = deposit.toKotlin(),
    minDeposit = minDeposit,
    withdrawal = withdrawal.toKotlin(),
    minWithdrawal = minWithdrawal,
    withdrawalFee = withdrawalFee,
    withdrawalFeePercent = withdrawalFeePercent,
    depositConfirmations = depositConfirmations,
)

internal fun RustCapability.toKotlin() = when (this) {
    RustCapability.ENABLED -> Capability.Enabled
    RustCapability.DISABLED -> Capability.Disabled
}
