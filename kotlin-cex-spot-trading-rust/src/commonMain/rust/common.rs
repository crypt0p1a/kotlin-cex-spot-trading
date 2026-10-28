//! Calls available on both the public and the private APIs

use crate::CexError;
use crate::client::RestClient;
use crate::models::{
    Candle, CandlesFromPair, CandlesFromPairs, GetCandles, OrderBook, OrderBookRequest,
    TradeHistory, TradeHistoryRequestWithDate, TradeHistoryRequestWithTrade,
};
use std::collections::HashMap;

pub(crate) async fn order_book(
    client: &RestClient,
    currency1: &str,
    currency2: &str,
) -> Result<OrderBook, CexError> {
    let request = OrderBookRequest {
        pair: format!("{currency1}-{currency2}"),
    };
    client.call_with("get_order_book", Some(&request)).await
}

pub(crate) async fn candles_from_pairs(
    client: &RestClient,
    request: &CandlesFromPairs,
) -> Result<HashMap<String, Vec<Candle>>, CexError> {
    client
        .call_with("get_candles", Some(&GetCandles::from(request)))
        .await
}

pub(crate) async fn candles_from_pair(
    client: &RestClient,
    request: &CandlesFromPair,
) -> Result<Vec<Candle>, CexError> {
    client
        .call_with("get_candles", Some(&GetCandles::from(request)))
        .await
}

pub(crate) async fn trade_history_with_trade(
    client: &RestClient,
    request: &TradeHistoryRequestWithTrade,
) -> Result<TradeHistory, CexError> {
    client.call_with("get_trade_history", Some(request)).await
}

pub(crate) async fn trade_history_with_date(
    client: &RestClient,
    request: &TradeHistoryRequestWithDate,
) -> Result<TradeHistory, CexError> {
    client.call_with("get_trade_history", Some(request)).await
}

/// Declares the calls of this module on an API holding a `client: RestClient`
macro_rules! common_api {
    ($api:ty) => {
        impl $api {
            /// Current order book snapshot of a trading pair
            ///
            /// https://trade.cex.io/docs/#rest-public-api-calls-order-book
            pub async fn order_book(
                &self,
                currency1: &str,
                currency2: &str,
            ) -> Result<$crate::models::OrderBook, $crate::CexError> {
                $crate::common::order_book(&self.client, currency1, currency2).await
            }

            /// Last historical OHLCV candle of several pairs, keyed by pair
            ///
            /// https://trade.cex.io/docs/#rest-public-api-calls-candles
            pub async fn candles_from_pairs(
                &self,
                request: &$crate::models::CandlesFromPairs,
            ) -> Result<
                std::collections::HashMap<String, Vec<$crate::models::Candle>>,
                $crate::CexError,
            > {
                $crate::common::candles_from_pairs(&self.client, request).await
            }

            /// Historical OHLCV candles of a pair
            ///
            /// https://trade.cex.io/docs/#rest-public-api-calls-candles
            pub async fn candles_from_pair(
                &self,
                request: &$crate::models::CandlesFromPair,
            ) -> Result<Vec<$crate::models::Candle>, $crate::CexError> {
                $crate::common::candles_from_pair(&self.client, request).await
            }

            /// Trades of a pair, filtered by trade ids
            ///
            /// https://trade.cex.io/docs/#rest-public-api-calls-trade-history
            pub async fn trade_history_with_trade(
                &self,
                request: &$crate::models::TradeHistoryRequestWithTrade,
            ) -> Result<$crate::models::TradeHistory, $crate::CexError> {
                $crate::common::trade_history_with_trade(&self.client, request).await
            }

            /// Trades of a pair, filtered by dates
            ///
            /// https://trade.cex.io/docs/#rest-public-api-calls-trade-history
            pub async fn trade_history_with_date(
                &self,
                request: &$crate::models::TradeHistoryRequestWithDate,
            ) -> Result<$crate::models::TradeHistory, $crate::CexError> {
                $crate::common::trade_history_with_date(&self.client, request).await
            }
        }
    };
}

pub(crate) use common_api;
