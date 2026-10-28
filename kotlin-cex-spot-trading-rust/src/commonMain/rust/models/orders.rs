use super::{OrderSide, SortOrder};
use crate::lenient;
use serde::{Deserialize, Serialize};

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
pub enum OrderType {
    Limit,
    Market,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
pub enum TimeInForce {
    Gtc,
    Ioc,
    Gtd,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
#[serde(rename_all = "SCREAMING_SNAKE_CASE")]
pub enum OrderStatus {
    PendingNew,
    New,
    PartiallyFilled,
    Filled,
    Expired,
    Rejected,
    PendingCancel,
    Cancelled,
}

/// https://trade.cex.io/docs/#rest-private-api-calls-orders
#[derive(Debug, Clone, PartialEq, Default, Serialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct OrderRequest {
    /// Order identifier assigned by Client. When present, the other parameters are ignored
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub client_order_id: Option<String>,
    /// Order identifier assigned by CEX.IO Spot Trading, ignored when client_order_id is present
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub order_id: Option<String>,
    /// true for the completed (archived) orders, the open orders otherwise
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub archived: Option<bool>,
    /// Currency pair, e.g. "BTC-USD". All the pairs when missing
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub pair: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub side: Option<OrderSide>,
    /// The accounts to look into, "" being the main account. All the accounts when missing
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub account_ids: Option<Vec<String>>,
    /// Sorted by serverCreateTimestamp, DESC when missing
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub sort_order: Option<SortOrder>,
    /// At most 1000, the default
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub page_size: Option<i32>,
    /// Starts at 0, the default
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub page_number: Option<i32>,
    /// UTC timestamp in milliseconds, mandatory for the archived orders
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub server_create_timestamp_from: Option<i64>,
    /// UTC timestamp in milliseconds, now when missing
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub server_create_timestamp_to: Option<i64>,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct OrderResult {
    #[serde(deserialize_with = "lenient::string")]
    pub order_id: String,
    #[serde(deserialize_with = "lenient::string")]
    pub client_order_id: String,
    #[serde(deserialize_with = "lenient::string")]
    pub client_id: String,
    /// None for the main account
    #[serde(default)]
    pub account_id: Option<String>,
    pub status: OrderStatus,
    pub status_is_final: bool,
    pub currency1: String,
    pub currency2: String,
    pub side: OrderSide,
    pub order_type: OrderType,
    #[serde(default)]
    pub time_in_force: Option<TimeInForce>,
    #[serde(default)]
    pub comment: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub reject_code: Option<i32>,
    #[serde(default)]
    pub reject_reason: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub executed_amount_ccy1: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub executed_amount_ccy2: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub requested_amount_ccy1: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub requested_amount_ccy2: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub initial_on_hold_amount_ccy1: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub initial_on_hold_amount_ccy2: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub fee_amount: Option<f64>,
    #[serde(default)]
    pub fee_currency: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub price: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub average_price: Option<f64>,
    #[serde(deserialize_with = "lenient::number")]
    pub client_create_timestamp: i64,
    #[serde(deserialize_with = "lenient::number")]
    pub server_create_timestamp: i64,
    #[serde(deserialize_with = "lenient::number")]
    pub last_update_timestamp: i64,
    /// Format YYYYMMDD-HH:mm:ss.SSS
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub expire_time: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub effective_time: Option<i64>,
}

/// https://trade.cex.io/docs/#rest-private-api-calls-new-order
#[derive(Debug, Clone, PartialEq, Serialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct NewOrder {
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub client_order_id: Option<String>,
    pub account_id: String,
    pub currency1: String,
    pub currency2: String,
    pub side: OrderSide,
    pub order_type: OrderType,
    /// UTC timestamp in milliseconds, now when missing
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub timestamp: Option<i64>,
    pub comment: String,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub amount_ccy1: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub amount_ccy2: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub price: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub expire_time: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub stop_price: Option<String>,
    pub time_in_force: TimeInForce,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct NewOrderAnswer {
    #[serde(deserialize_with = "lenient::string")]
    pub client_id: String,
    pub account_id: String,
    #[serde(deserialize_with = "lenient::string")]
    pub order_id: String,
    #[serde(deserialize_with = "lenient::string")]
    pub client_order_id: String,
    pub status: OrderStatus,
    pub currency1: String,
    pub currency2: String,
    pub side: OrderSide,
    pub order_type: OrderType,
    #[serde(default = "crate::now_millis", deserialize_with = "lenient::number")]
    pub timestamp: i64,
    #[serde(default)]
    pub comment: Option<String>,
    #[serde(default)]
    pub order_reject_reason: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub reject_code: Option<i32>,
    #[serde(default)]
    pub reject_reason: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub executed_amount_ccy1: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub executed_amount_ccy2: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub requested_amount_ccy1: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub requested_amount_ccy2: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub amount_ccy2: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub fee_amount: Option<String>,
    #[serde(default)]
    pub fee_currency: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub price: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub average_price: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_string")]
    pub expire_time: Option<String>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub effective_time: Option<i64>,
    #[serde(default)]
    pub time_in_force: Option<TimeInForce>,
}

/// https://trade.cex.io/docs/#rest-private-api-calls-cancel-order
#[derive(Debug, Clone, PartialEq, Serialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct CancelOrder {
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub order_id: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub client_order_id: Option<String>,
    pub cancel_request_id: String,
    /// UTC timestamp in milliseconds, now when missing
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub timestamp: Option<i64>,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[serde(rename_all = "camelCase")]
pub(crate) struct CancelOrders {
    pub client_order_ids: Vec<String>,
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn omits_the_missing_parameters() {
        let request = OrderRequest {
            client_order_id: Some("abc".into()),
            ..Default::default()
        };

        assert_eq!(
            serde_json::to_string(&request).unwrap(),
            r#"{"clientOrderId":"abc"}"#
        );
    }

    #[test]
    fn encodes_the_enums_like_the_api() {
        assert_eq!(
            serde_json::to_string(&OrderStatus::PartiallyFilled).unwrap(),
            r#""PARTIALLY_FILLED""#
        );
        assert_eq!(
            serde_json::to_string(&TimeInForce::Gtc).unwrap(),
            r#""GTC""#
        );
        assert_eq!(
            serde_json::to_string(&OrderSide::Sell).unwrap(),
            r#""SELL""#
        );
        assert_eq!(serde_json::to_string(&SortOrder::Asc).unwrap(), r#""ASC""#);
    }
}
