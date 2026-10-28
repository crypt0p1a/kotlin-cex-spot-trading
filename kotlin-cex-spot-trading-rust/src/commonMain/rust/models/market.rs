use super::OrderSide;
use crate::lenient;
use serde::{Deserialize, Deserializer, Serialize};
use std::collections::HashMap;

/// https://trade.cex.io/docs/#rest-public-api-calls-ticker
///
/// Every field is optional: CEX.IO only sends a subset of them, depending on the pair
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct Ticker {
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub best_bid: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub best_ask: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub best_bid_change: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_percentage")]
    pub best_bid_change_percentage: Option<f32>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub best_ask_change: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_percentage")]
    pub best_ask_change_percentage: Option<f32>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub volume30d: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub low: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub high: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub volume: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub quote_volume: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub last_trade_volume: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub last: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub last_trade_price: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub price_change: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub price_change_percentage: Option<f64>,
    #[serde(
        default,
        rename = "lastTradeDateISO",
        deserialize_with = "lenient::opt_string"
    )]
    pub last_trade_date_iso: Option<String>,
    #[serde(
        default,
        rename = "volumeUSD",
        deserialize_with = "lenient::opt_number"
    )]
    pub volume_usd: Option<f64>,
}

/// https://trade.cex.io/docs/#rest-public-api-calls-server-time
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
pub struct ServerTime {
    #[serde(deserialize_with = "lenient::number")]
    pub timestamp: f64,
    #[serde(rename = "ISODate")]
    pub iso_date: String,
}

/// https://trade.cex.io/docs/#rest-public-api-calls-pairs-info
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct PairInfo {
    pub base: String,
    pub quote: String,
    #[serde(deserialize_with = "lenient::number")]
    pub base_min: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub base_max: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub base_lot_size: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub quote_min: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub quote_max: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub quote_lot_size: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub base_precision: i32,
    #[serde(deserialize_with = "lenient::number")]
    pub quote_precision: i32,
    #[serde(deserialize_with = "lenient::number")]
    pub price_precision: i32,
    #[serde(deserialize_with = "lenient::number")]
    pub min_price: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub max_price: f64,
}

/// https://trade.cex.io/docs/#rest-public-api-calls-currencies-info
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct CurrencyInfo {
    pub currency: String,
    #[serde(deserialize_with = "lenient::string")]
    pub wallet_deposit: String,
    pub wallet_withdrawal: bool,
    pub fiat: bool,
    #[serde(deserialize_with = "lenient::number")]
    pub precision: i32,
    #[serde(deserialize_with = "lenient::number")]
    pub wallet_precision: i32,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
#[serde(rename_all = "camelCase")]
pub enum Capability {
    Enabled,
    Disabled,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct Blockchain {
    #[serde(rename = "type")]
    pub blockchain_type: String,
    pub deposit: Capability,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub min_deposit: Option<f64>,
    pub withdrawal: Capability,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub min_withdrawal: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub withdrawal_fee: Option<f64>,
    /// Disappeared from the official documentation
    #[serde(default, deserialize_with = "lenient::opt_percentage")]
    pub withdrawal_fee_percent: Option<f32>,
    #[serde(deserialize_with = "lenient::number")]
    pub deposit_confirmations: i32,
}

/// https://trade.cex.io/docs/#rest-public-api-calls-processing-info
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
pub struct ProcessingInfo {
    pub name: String,
    pub blockchains: HashMap<String, Blockchain>,
}

#[derive(Debug, Clone, Copy, PartialEq)]
#[derive(uniffi::Record)]
pub struct PriceAmount {
    pub price: f64,
    pub amount: f64,
}

/// https://trade.cex.io/docs/#rest-public-api-calls-order-book
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
pub struct OrderBook {
    #[serde(deserialize_with = "lenient::number")]
    pub timestamp: i64,
    pub currency1: String,
    pub currency2: String,
    #[serde(deserialize_with = "price_amounts")]
    pub bids: Vec<PriceAmount>,
    #[serde(deserialize_with = "price_amounts")]
    pub asks: Vec<PriceAmount>,
}

#[derive(Deserialize)]
struct LenientF64(#[serde(deserialize_with = "lenient::number")] f64);

/// The order book levels are sent as [[price, amount], ...]
fn price_amounts<'de, D: Deserializer<'de>>(deserializer: D) -> Result<Vec<PriceAmount>, D::Error> {
    let levels = Vec::<Vec<LenientF64>>::deserialize(deserializer)?;

    levels
        .into_iter()
        .map(|level| match (level.first(), level.last()) {
            (Some(price), Some(amount)) if level.len() >= 2 => Ok(PriceAmount {
                price: price.0,
                amount: amount.0,
            }),
            _ => Err(serde::de::Error::custom("expected a [price, amount] level")),
        })
        .collect()
}

#[derive(Debug, Clone, PartialEq, Serialize)]
pub(crate) struct OrderBookRequest {
    pub pair: String,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
pub enum CandleResolution {
    #[serde(rename = "1m")]
    Res1m,
    #[serde(rename = "5m")]
    Res5m,
    #[serde(rename = "15m")]
    Res15m,
    #[serde(rename = "30m")]
    Res30m,
    #[serde(rename = "1h")]
    Res1h,
    #[serde(rename = "2h")]
    Res2h,
    #[serde(rename = "4h")]
    Res4h,
    #[serde(rename = "1d")]
    Res1d,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
pub enum DataType {
    #[serde(rename = "bestAsk")]
    BestAsk,
    #[serde(rename = "bestBid")]
    BestBid,
}

/// https://trade.cex.io/docs/#rest-public-api-calls-candles
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct Candle {
    #[serde(deserialize_with = "lenient::number")]
    pub timestamp: i64,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub open: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub high: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub low: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub close: Option<f64>,
    #[serde(default, deserialize_with = "lenient::opt_number")]
    pub volume: Option<f64>,
    pub resolution: CandleResolution,
    #[serde(default)]
    pub is_closed: Option<bool>,
    #[serde(rename = "timestampISO")]
    pub timestamp_iso: String,
}

/// Candles of a single pair, `from_ms` and `to_ms` are UTC timestamps in milliseconds
#[derive(Debug, Clone, PartialEq)]
#[derive(uniffi::Record)]
pub struct CandlesFromPair {
    pub pair: String,
    pub from_ms: Option<i64>,
    pub to_ms: Option<i64>,
    pub limit: Option<i32>,
    pub data_type: DataType,
    pub resolution: CandleResolution,
}

/// The last candle of several pairs, `from_ms` and `to_ms` are UTC timestamps in milliseconds
#[derive(Debug, Clone, PartialEq)]
#[derive(uniffi::Record)]
pub struct CandlesFromPairs {
    pub pairs_list: Vec<String>,
    pub from_ms: Option<i64>,
    pub to_ms: Option<i64>,
    pub data_type: DataType,
    pub resolution: CandleResolution,
}

#[derive(Debug, Clone, PartialEq, Serialize)]
#[serde(rename_all = "camelCase")]
pub(crate) struct GetCandles {
    #[serde(skip_serializing_if = "Option::is_none")]
    pub pair: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    pub pairs_list: Option<Vec<String>>,
    #[serde(rename = "fromISO", skip_serializing_if = "Option::is_none")]
    pub from_iso: Option<i64>,
    #[serde(rename = "toISO", skip_serializing_if = "Option::is_none")]
    pub to_iso: Option<i64>,
    #[serde(skip_serializing_if = "Option::is_none")]
    pub limit: Option<i32>,
    pub data_type: DataType,
    pub resolution: CandleResolution,
}

impl From<&CandlesFromPair> for GetCandles {
    fn from(request: &CandlesFromPair) -> Self {
        Self {
            pair: Some(request.pair.clone()),
            pairs_list: None,
            from_iso: request.from_ms,
            to_iso: request.to_ms,
            limit: request.limit,
            data_type: request.data_type,
            resolution: request.resolution,
        }
    }
}

impl From<&CandlesFromPairs> for GetCandles {
    fn from(request: &CandlesFromPairs) -> Self {
        Self {
            pair: None,
            pairs_list: Some(request.pairs_list.clone()),
            from_iso: request.from_ms,
            to_iso: request.to_ms,
            limit: Some(1),
            data_type: request.data_type,
            resolution: request.resolution,
        }
    }
}

/// https://trade.cex.io/docs/#rest-public-api-calls-trade-history
#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct TradeHistory {
    #[serde(deserialize_with = "lenient::number")]
    pub page_size: i32,
    pub trades: Vec<TradeFromHistory>,
}

#[derive(Debug, Clone, PartialEq, Deserialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct TradeFromHistory {
    pub side: OrderSide,
    #[serde(rename = "dateISO")]
    pub date_iso: String,
    #[serde(deserialize_with = "lenient::number")]
    pub price: f64,
    #[serde(deserialize_with = "lenient::number")]
    pub amount: f64,
    #[serde(deserialize_with = "lenient::string")]
    pub trade_id: String,
}

#[derive(Debug, Clone, PartialEq, Serialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct TradeHistoryRequestWithTrade {
    pub pair: String,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub side: Option<OrderSide>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub from_trade_id: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub to_trade_id: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub page_size: Option<i32>,
}

#[derive(Debug, Clone, PartialEq, Serialize)]
#[derive(uniffi::Record)]
#[serde(rename_all = "camelCase")]
pub struct TradeHistoryRequestWithDate {
    pub pair: String,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub side: Option<OrderSide>,
    #[serde(rename = "fromDateISO", skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub from_date_iso: Option<String>,
    #[serde(rename = "toDateISO", skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub to_date_iso: Option<String>,
    #[serde(skip_serializing_if = "Option::is_none")]
    #[uniffi(default = None)]
    pub page_size: Option<i32>,
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn decodes_the_order_book_levels() {
        let book: OrderBook = serde_json::from_str(
            r#"{"timestamp":1700000000000,"currency1":"BTC","currency2":"USD",
                "bids":[["35000.1","0.5"],[34999,1]],"asks":[]}"#,
        )
        .unwrap();

        assert_eq!(
            book.bids,
            vec![
                PriceAmount {
                    price: 35000.1,
                    amount: 0.5
                },
                PriceAmount {
                    price: 34999.0,
                    amount: 1.0
                },
            ]
        );
        assert!(book.asks.is_empty());
    }

    #[test]
    fn encodes_the_candles_requests() {
        let request = GetCandles::from(&CandlesFromPairs {
            pairs_list: vec!["BTC-USD".into()],
            from_ms: None,
            to_ms: Some(42),
            data_type: DataType::BestAsk,
            resolution: CandleResolution::Res1h,
        });

        assert_eq!(
            serde_json::to_string(&request).unwrap(),
            r#"{"pairsList":["BTC-USD"],"toISO":42,"limit":1,"dataType":"bestAsk","resolution":"1h"}"#
        );
    }

    #[test]
    fn decodes_the_tickers() {
        let ticker: Ticker = serde_json::from_str(
            r#"{"bestBid":"35000.1","bestAsk":"35001","bestBidChange":"1.2",
                "bestBidChangePercentage":"0.01","bestAskChange":"-1",
                "bestAskChangePercentage":"-0.02","volume30d":"1000","low":"34000",
                "high":"36000","volume":"10","quoteVolume":"350000","lastTradeVolume":"0.1",
                "last":"35000.5","lastTradePrice":"35000.5","priceChange":"100",
                "priceChangePercentage":"0.28","lastTradeDateISO":"2025-01-01T00:00:00.000Z",
                "volumeUSD":"350000","unknownField":true}"#,
        )
        .unwrap();

        assert_eq!(ticker.best_bid, Some(35000.1));
        assert_eq!(ticker.best_ask_change_percentage, Some(-0.02));
        assert_eq!(
            ticker.last_trade_date_iso.as_deref(),
            Some("2025-01-01T00:00:00.000Z")
        );
    }

    #[test]
    fn decodes_the_partial_tickers() {
        // the current shape of the answers
        let ticker: Ticker = serde_json::from_str(
            r#"{"bestBid":"81250.0","bestAsk":"81250.1","last":"81250.1",
                "volume":"38.82019441","volumeUSD":"3144763.65","priceChangePercentage":"0.58"}"#,
        )
        .unwrap();

        assert_eq!(ticker.last, Some(81250.1));
        assert_eq!(ticker.volume_usd, Some(3144763.65));
        assert_eq!(ticker.best_bid_change, None);
    }
}
