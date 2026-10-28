use crate::client::{Endpoint, RestClient};
use crate::common::common_api;
use crate::models::{
    Currencies, CurrencyInfo, PairInfo, Pairs, ProcessingInfo, ServerTime, Ticker,
};
use crate::{CexError, RateLimiter, RestOptions};
use std::collections::HashMap;
use std::sync::Arc;

/// Public API of CEX.IO Spot Trading, no credentials required
pub struct PublicApi {
    client: RestClient,
}

common_api!(PublicApi);

impl PublicApi {
    pub fn new(options: RestOptions, rate_limiter: Arc<RateLimiter>) -> Result<Self, CexError> {
        Ok(Self {
            client: RestClient::new(Endpoint::Public, None, options, rate_limiter)?,
        })
    }

    /// Current prices, 24h price & volume changes, last trade of the pairs, keyed by pair
    ///
    /// https://trade.cex.io/docs/#rest-public-api-calls-ticker
    pub async fn tickers(&self, pairs: &[String]) -> Result<HashMap<String, Ticker>, CexError> {
        self.client
            .call_with("get_ticker", Some(&Pairs { pairs }))
            .await
    }

    /// Current time of the Spot Trading server
    ///
    /// https://trade.cex.io/docs/#rest-public-api-calls-server-time
    pub async fn server_time(&self) -> Result<ServerTime, CexError> {
        self.client.call("get_server_time").await
    }

    /// Parameters of the trading pairs, all of them when `pairs` is empty
    ///
    /// https://trade.cex.io/docs/#rest-public-api-calls-pairs-info
    pub async fn pairs_info(&self, pairs: &[String]) -> Result<Vec<PairInfo>, CexError> {
        self.client
            .call_with("get_pairs_info", Some(&Pairs { pairs }))
            .await
    }

    /// Parameters of the currencies and their deposit/withdrawal availability with CEX.IO Wallet
    ///
    /// https://trade.cex.io/docs/#rest-public-api-calls-currencies-info
    pub async fn currency_infos(&self) -> Result<Vec<CurrencyInfo>, CexError> {
        self.client.call("get_currencies_info").await
    }

    /// Deposit and withdrawal options of the cryptocurrencies with external wallets, per blockchain
    ///
    /// https://trade.cex.io/docs/#rest-public-api-calls-processing-info
    pub async fn processing_info(
        &self,
        currencies: &[String],
    ) -> Result<HashMap<String, ProcessingInfo>, CexError> {
        self.client
            .call_with("get_processing_info", Some(&Currencies { currencies }))
            .await
    }
}
