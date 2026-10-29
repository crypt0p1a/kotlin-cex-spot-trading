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
import eu.codlab.cex.spot.trading.groups.funds.ApprovalStatus
import eu.codlab.cex.spot.trading.groups.funds.DepositToOrWithdrawFromWalletResult
import eu.codlab.cex.spot.trading.groups.funds.DepositToOrWithdrawalFromWalletRequest
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionHistory
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionHistoryRequest
import eu.codlab.cex.spot.trading.groups.history.transactions.TransactionType
import eu.codlab.cex.spot.trading.groups.orders.OrderType
import eu.codlab.cex.spot.trading.groups.orders.cancel.CancelOrder
import eu.codlab.cex.spot.trading.groups.orders.news.NewOrder
import eu.codlab.cex.spot.trading.groups.orders.news.NewOrderAnswer
import eu.codlab.cex.spot.trading.groups.orders.news.TimeInForce
import eu.codlab.cex.spot.trading.groups.volume.Volume
import eu.codlab.cex.spot.trading.models.Order
import eu.codlab.cex.spot.trading.models.OrderRequest
import eu.codlab.cex.spot.trading.models.OrderResult
import eu.codlab.cex.spot.trading.models.OrderStatus
import eu.codlab.cex.spot.trading.models.TradingFee
import eu.codlab.cex.spot.trading.models.TradingFees
import eu.codlab.cex.spot.trading.rust.bindings.AccountBalance as RustAccountBalance
import eu.codlab.cex.spot.trading.rust.bindings.AccountStatusRequest as RustAccountStatusRequest
import eu.codlab.cex.spot.trading.rust.bindings.AccountStatusResult as RustAccountStatusResult
import eu.codlab.cex.spot.trading.rust.bindings.ApprovalStatus as RustApprovalStatus
import eu.codlab.cex.spot.trading.rust.bindings.BalanceForAccount as RustBalanceForAccount
import eu.codlab.cex.spot.trading.rust.bindings.CancelOrder as RustCancelOrder
import eu.codlab.cex.spot.trading.rust.bindings.CreateAccountResult as RustCreateAccountResult
import eu.codlab.cex.spot.trading.rust.bindings.DepositToOrWithdrawFromWalletResult as RustDepositToOrWithdrawFromWalletResult
import eu.codlab.cex.spot.trading.rust.bindings.DepositToOrWithdrawalFromWalletRequest as RustDepositToOrWithdrawalFromWalletRequest
import eu.codlab.cex.spot.trading.rust.bindings.FeeStrategy as RustFeeStrategy
import eu.codlab.cex.spot.trading.rust.bindings.FeeVolume as RustFeeVolume
import eu.codlab.cex.spot.trading.rust.bindings.NewOrder as RustNewOrder
import eu.codlab.cex.spot.trading.rust.bindings.NewOrderAnswer as RustNewOrderAnswer
import eu.codlab.cex.spot.trading.rust.bindings.OrderRequest as RustOrderRequest
import eu.codlab.cex.spot.trading.rust.bindings.OrderResult as RustOrderResult
import eu.codlab.cex.spot.trading.rust.bindings.OrderSide as RustOrderSide
import eu.codlab.cex.spot.trading.rust.bindings.OrderStatus as RustOrderStatus
import eu.codlab.cex.spot.trading.rust.bindings.OrderType as RustOrderType
import eu.codlab.cex.spot.trading.rust.bindings.SortOrder as RustSortOrder
import eu.codlab.cex.spot.trading.rust.bindings.StrategyConfig as RustStrategyConfig
import eu.codlab.cex.spot.trading.rust.bindings.TimeInForce as RustTimeInForce
import eu.codlab.cex.spot.trading.rust.bindings.TradingFee as RustTradingFee
import eu.codlab.cex.spot.trading.rust.bindings.TradingFeeBalancesForAccount as RustTradingFeeBalancesForAccount
import eu.codlab.cex.spot.trading.rust.bindings.TradingFees as RustTradingFees
import eu.codlab.cex.spot.trading.rust.bindings.TransactionHistory as RustTransactionHistory
import eu.codlab.cex.spot.trading.rust.bindings.TransactionHistoryRequest as RustTransactionHistoryRequest
import eu.codlab.cex.spot.trading.rust.bindings.TransactionType as RustTransactionType
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

internal fun OrderRequest.toRust() = RustOrderRequest(
    clientOrderId = clientOrderId,
    orderId = orderId,
    archived = archived,
    pair = pair,
    side = side?.let { side ->
        RustOrderSide.entries.find { it.name.equals(side, ignoreCase = true) }
            ?: throw IllegalArgumentException("Unknown order side $side, expected BUY or SELL")
    },
    accountIds = accountIds,
    sortOrder = sortOrder.toRust(),
    pageSize = pageSize,
    pageNumber = pageNumber,
    serverCreateTimestampFrom = serverCreateTimestampFrom,
    serverCreateTimestampTo = serverCreateTimestampTo,
)

internal fun Order.toRust() = when (this) {
    Order.Desc -> RustSortOrder.DESC
    Order.Asc -> RustSortOrder.ASC
}

internal fun RustOrderStatus.toKotlin() = when (this) {
    RustOrderStatus.PENDING_NEW -> OrderStatus.PENDING_NEW
    RustOrderStatus.NEW -> OrderStatus.NEW
    RustOrderStatus.PARTIALLY_FILLED -> OrderStatus.PARTIALLY_FILLED
    RustOrderStatus.FILLED -> OrderStatus.FILLED
    RustOrderStatus.EXPIRED -> OrderStatus.EXPIRED
    RustOrderStatus.REJECTED -> OrderStatus.REJECTED
    RustOrderStatus.PENDING_CANCEL -> OrderStatus.PENDING_CANCEL
    RustOrderStatus.CANCELLED -> OrderStatus.CANCELLED
}

internal fun OrderType.toRust() = when (this) {
    OrderType.Limit -> RustOrderType.LIMIT
    OrderType.Market -> RustOrderType.MARKET
}

internal fun RustOrderType.toKotlin() = when (this) {
    RustOrderType.LIMIT -> OrderType.Limit
    RustOrderType.MARKET -> OrderType.Market
}

internal fun TimeInForce.toRust() = when (this) {
    TimeInForce.GTC -> RustTimeInForce.GTC
    TimeInForce.IOC -> RustTimeInForce.IOC
    TimeInForce.GTD -> RustTimeInForce.GTD
}

internal fun RustTimeInForce.toKotlin() = when (this) {
    RustTimeInForce.GTC -> TimeInForce.GTC
    RustTimeInForce.IOC -> TimeInForce.IOC
    RustTimeInForce.GTD -> TimeInForce.GTD
}

internal fun RustOrderResult.toKotlin() = OrderResult(
    orderId = orderId,
    clientOrderId = clientOrderId,
    clientId = clientId,
    accountId = accountId,
    status = status.toKotlin(),
    statusIsFinal = statusIsFinal,
    currency1 = currency1,
    currency2 = currency2,
    side = side.toKotlin(),
    orderType = orderType.toKotlin(),
    timeInForce = timeInForce?.toKotlin(),
    comment = comment,
    rejectCode = rejectCode,
    rejectReason = rejectReason,
    executedAmountCcy1 = executedAmountCcy1,
    executedAmountCcy2 = executedAmountCcy2,
    requestedAmountCcy1 = requestedAmountCcy1,
    requestedAmountCcy2 = requestedAmountCcy2,
    initialOnHoldAmountCcy1 = initialOnHoldAmountCcy1,
    initialOnHoldAmountCcy2 = initialOnHoldAmountCcy2,
    feeAmount = feeAmount,
    feeCurrency = feeCurrency,
    price = price,
    averagePrice = averagePrice,
    clientCreateTimestamp = clientCreateTimestamp,
    serverCreateTimestamp = serverCreateTimestamp,
    lastUpdateTimestamp = lastUpdateTimestamp,
    expireTime = expireTime?.toLongOrNull(),
    effectiveTime = effectiveTime,
)

internal fun NewOrder.toRust() = RustNewOrder(
    clientOrderId = clientOrderId,
    accountId = accountId,
    currency1 = currency1,
    currency2 = currency2,
    side = side.toRust(),
    orderType = orderType.toRust(),
    timestamp = timestamp,
    comment = comment,
    amountCcy1 = amountCcy1,
    amountCcy2 = amountCcy2,
    price = price,
    expireTime = expireTime,
    stopPrice = stopPrice,
    timeInForce = timeInForce.toRust(),
)

internal fun RustNewOrderAnswer.toKotlin() = NewOrderAnswer(
    clientId = clientId,
    accountId = accountId,
    orderId = orderId,
    clientOrderId = clientOrderId,
    status = status.toKotlin(),
    currency1 = currency1,
    currency2 = currency2,
    side = side.toKotlin(),
    orderType = orderType.toKotlin(),
    timestamp = timestamp,
    comment = comment,
    orderRejectReason = orderRejectReason,
    rejectCode = rejectCode,
    rejectReason = rejectReason,
    executedAmountCcy1 = executedAmountCcy1,
    executedAmountCcy2 = executedAmountCcy2,
    requestedAmountCcy1 = requestedAmountCcy1,
    requestedAmountCcy2 = requestedAmountCcy2,
    amountCcy2 = amountCcy2,
    feeAmount = feeAmount,
    feeCurrency = feeCurrency,
    price = price,
    averagePrice = averagePrice,
    expireTime = expireTime?.toLongOrNull(),
    effectiveTime = effectiveTime,
    timeInForce = timeInForce?.toKotlin(),
)

internal fun CancelOrder.toRust() = RustCancelOrder(
    orderId = orderId,
    clientOrderId = clientOrderId,
    cancelRequestId = cancelRequestId,
    timestamp = timestamp,
)

internal fun TransactionHistoryRequest.toRust() = RustTransactionHistoryRequest(
    accountId = accountId,
    transactionType = type.toRust(),
    dateFrom = dateFrom,
    dateTo = dateTo,
    sortOrder = sortOrder?.toRust(),
    pageSize = pageSize,
    pageNumber = pageNumber,
)

internal fun TransactionType.toRust() = when (this) {
    TransactionType.Trade -> RustTransactionType.TRADE
    TransactionType.Deposit -> RustTransactionType.DEPOSIT
    TransactionType.Withdraw -> RustTransactionType.WITHDRAW
    TransactionType.InternalTransfer -> RustTransactionType.INTERNAL_TRANSFER
    TransactionType.Commission -> RustTransactionType.COMMISSION
}

internal fun RustTransactionType.toKotlin() = when (this) {
    RustTransactionType.TRADE -> TransactionType.Trade
    RustTransactionType.DEPOSIT -> TransactionType.Deposit
    RustTransactionType.WITHDRAW -> TransactionType.Withdraw
    RustTransactionType.INTERNAL_TRANSFER -> TransactionType.InternalTransfer
    RustTransactionType.COMMISSION -> TransactionType.Commission
}

internal fun RustTransactionHistory.toKotlin() = TransactionHistory(
    transactionId = transactionId,
    timestamp = timestamp,
    accountId = accountId,
    type = transactionType.toKotlin(),
    amount = amount,
    details = details,
    currency = currency,
)

internal fun DepositToOrWithdrawalFromWalletRequest.toRust() =
    RustDepositToOrWithdrawalFromWalletRequest(
        clientTxId = clientTxId,
        accountId = accountId,
        currency = currency,
        amount = amount,
    )

internal fun RustDepositToOrWithdrawFromWalletResult.toKotlin() =
    DepositToOrWithdrawFromWalletResult(
        clientTxId = clientTxId,
        accountId = accountId,
        currency = currency,
        status = status.toKotlin(),
    )

internal fun RustApprovalStatus.toKotlin() = when (this) {
    RustApprovalStatus.REJECTED -> ApprovalStatus.REJECTED
    RustApprovalStatus.PENDING -> ApprovalStatus.PENDING
    RustApprovalStatus.APPROVED -> ApprovalStatus.APPROVED
}
