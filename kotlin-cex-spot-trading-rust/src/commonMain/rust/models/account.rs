use super::SortOrder;
use crate::lenient;
use serde::{Deserialize, Serialize};
use std::collections::HashMap;

/// https://trade.cex.io/docs/#rest-private-api-calls-wallet-balance
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
pub struct AccountBalance {
    #[serde(deserialize_with = "lenient::number")]
    pub balance: f64,
}

/// https://trade.cex.io/docs/#rest-private-api-calls-account-status-v3
#[derive(Debug, Clone, PartialEq, Default, Serialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct AccountStatusRequest {
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub currencies: Option<Vec<String>>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub account_ids: Option<Vec<String>>,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct AccountStatusResult {
    pub converted_currency: String,
    pub balances_per_accounts: HashMap<String, HashMap<String, BalanceForAccount>>,
    #[serde(default)]
    pub trading_fee_balances_per_accounts:
        Option<HashMap<String, HashMap<String, TradingFeeBalancesForAccount>>>,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct BalanceForAccount {
    #[serde(deserialize_with = "lenient::number")]
    pub balance: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub balance_on_hold: f64,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub balance_in_converted_currency: Option<f64>,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct TradingFeeBalancesForAccount {
    #[serde(deserialize_with = "lenient::number")]
    pub balance: f64,
    pub expiration_date: String,
}

#[derive(Debug, Clone, PartialEq, Serialize)]
#[serde(rename_all = "camelCase")]
pub(crate) struct CreateAccountRequest<'a> {
    pub account_id: &'a str,
    pub currency: &'a str,
}

/// https://trade.cex.io/docs/#rest-private-api-calls-create-account
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct CreateAccountResult {
    pub account_id: String,
}

/// https://trade.cex.io/docs/#rest-private-api-calls-current-fee
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct TradingFees {
    pub trading_fee: HashMap<String, TradingFee>,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
pub struct TradingFee {
    #[serde(deserialize_with = "lenient::percentage")]
    pub percent: f32,
}

/// https://trade.cex.io/docs/#rest-private-api-calls-fee-strategy
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct FeeStrategy {
    pub strategy_config: StrategyConfig,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
pub struct StrategyConfig {
    #[serde(rename = "default")]
    pub default_fees: Vec<FeeVolume>,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
pub struct FeeVolume {
    #[serde(deserialize_with = "lenient::number")]
    pub fee: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub volume: i64,
}

/// https://trade.cex.io/docs/#rest-private-api-calls-volume
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
pub struct Volume {
    pub period: String,
    #[serde(deserialize_with = "lenient::number")]
    pub volume: f64,
    pub currency: String,
}

#[derive(Debug, Clone, PartialEq, Serialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct DepositToOrWithdrawalFromWalletRequest {
    pub client_tx_id: String,
    pub account_id: String,
    pub currency: String,
    pub amount: String,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
#[serde(rename_all = "camelCase")]
pub enum ApprovalStatus {
    Rejected,
    Pending,
    Approved,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct DepositToOrWithdrawFromWalletResult {
    pub client_tx_id: String,
    pub account_id: String,
    pub currency: String,
    pub status: ApprovalStatus,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
#[serde(rename_all = "camelCase")]
pub enum TransactionType {
    Trade,
    Deposit,
    Withdraw,
    InternalTransfer,
    Commission,
}

/// https://trade.cex.io/docs/#rest-private-api-calls-transaction-history
#[derive(Debug, Clone, PartialEq, Serialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct TransactionHistoryRequest {
    pub account_id: String,
    #[serde(rename = "type")]
    pub transaction_type: TransactionType,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub date_from: Option<i64>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub date_to: Option<i64>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub sort_order: Option<SortOrder>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub page_size: Option<i32>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub page_number: Option<i32>,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct TransactionHistory {
    #[serde(deserialize_with = "lenient::string")]
    pub transaction_id: String,
    #[serde(deserialize_with = "lenient::string")]
    pub timestamp: String,
    pub account_id: String,
    #[serde(rename = "type")]
    pub transaction_type: TransactionType,
    #[serde(deserialize_with = "lenient::number")]
    pub amount: f64,
    pub details: String,
    pub currency: String,
}
