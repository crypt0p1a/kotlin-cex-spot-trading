//! UniFFI facade of the crate: the models and errors are exported where they are declared, the APIs
//! are wrapped here as objects with async methods.

use crate::models::{
    AccountBalance, AccountStatusRequest, AccountStatusResult, CancelOrder, Candle,
    CandlesFromPair, CandlesFromPairs, CreateAccountResult, CurrencyInfo,
    DepositToOrWithdrawFromWalletResult, DepositToOrWithdrawalFromWalletRequest, FeeStrategy,
    NewOrder, NewOrderAnswer, OrderBook, OrderRequest, OrderResult, PairInfo, ProcessingInfo,
    ServerTime, Ticker, TradeHistory, TradeHistoryRequestWithDate, TradeHistoryRequestWithTrade,
    TradingFees, TransactionHistory, TransactionHistoryRequest, Volume,
};
use crate::{CexError, RestOptions};
use std::collections::HashMap;
use std::sync::Arc;
use std::time::Duration;

/// Receives the logs of the rate limiter
#[uniffi::export(with_foreign)]
pub trait CexLogger: Send + Sync {
    fn log(&self, tag: String, message: String);
}

/// The default options of the HTTP client
#[uniffi::export]
pub fn default_rest_options() -> RestOptions {
    RestOptions::default()
}

/// Token bucket shared by the APIs given to their constructor
#[derive(uniffi::Object)]
pub struct CexRateLimiter {
    inner: Arc<crate::RateLimiter>,
}

#[uniffi::export]
impl CexRateLimiter {
    /// `pool_maximum` tokens at most, a new one every `token_interval_ms`
    #[uniffi::constructor]
    pub fn new(
        pool_maximum: u32,
        token_interval_ms: u64,
        logger: Option<Arc<dyn CexLogger>>,
    ) -> Arc<Self> {
        let logger = logger.map(|logger| -> crate::Logger {
            Arc::new(move |tag: &str, message: &str| logger.log(tag.into(), message.into()))
        });

        Arc::new(Self {
            inner: Arc::new(crate::RateLimiter::new(
                pool_maximum,
                Duration::from_millis(token_interval_ms),
                logger,
            )),
        })
    }
}

/// Exports the calls shared by the public and the private APIs
macro_rules! export_common_api {
    ($api:ty) => {
        #[uniffi::export(async_runtime = "tokio")]
        impl $api {
            pub async fn order_book(
                &self,
                currency1: String,
                currency2: String,
            ) -> Result<OrderBook, CexError> {
                self.inner.order_book(&currency1, &currency2).await
            }

            pub async fn candles_from_pairs(
                &self,
                request: CandlesFromPairs,
            ) -> Result<HashMap<String, Vec<Candle>>, CexError> {
                self.inner.candles_from_pairs(&request).await
            }

            pub async fn candles_from_pair(
                &self,
                request: CandlesFromPair,
            ) -> Result<Vec<Candle>, CexError> {
                self.inner.candles_from_pair(&request).await
            }

            pub async fn trade_history_with_trade(
                &self,
                request: TradeHistoryRequestWithTrade,
            ) -> Result<TradeHistory, CexError> {
                self.inner.trade_history_with_trade(&request).await
            }

            pub async fn trade_history_with_date(
                &self,
                request: TradeHistoryRequestWithDate,
            ) -> Result<TradeHistory, CexError> {
                self.inner.trade_history_with_date(&request).await
            }
        }
    };
}

#[derive(uniffi::Object)]
pub struct CexPublicApi {
    inner: crate::PublicApi,
}

export_common_api!(CexPublicApi);

#[uniffi::export(async_runtime = "tokio")]
impl CexPublicApi {
    #[uniffi::constructor]
    pub fn new(
        options: RestOptions,
        rate_limiter: Arc<CexRateLimiter>,
    ) -> Result<Arc<Self>, CexError> {
        Ok(Arc::new(Self {
            inner: crate::PublicApi::new(options, rate_limiter.inner.clone())?,
        }))
    }

    pub async fn tickers(&self, pairs: Vec<String>) -> Result<HashMap<String, Ticker>, CexError> {
        self.inner.tickers(&pairs).await
    }

    pub async fn server_time(&self) -> Result<ServerTime, CexError> {
        self.inner.server_time().await
    }

    pub async fn pairs_info(&self, pairs: Vec<String>) -> Result<Vec<PairInfo>, CexError> {
        self.inner.pairs_info(&pairs).await
    }

    pub async fn currency_infos(&self) -> Result<Vec<CurrencyInfo>, CexError> {
        self.inner.currency_infos().await
    }

    pub async fn processing_info(
        &self,
        currencies: Vec<String>,
    ) -> Result<HashMap<String, ProcessingInfo>, CexError> {
        self.inner.processing_info(&currencies).await
    }
}

#[derive(uniffi::Object)]
pub struct CexPrivateApi {
    inner: crate::PrivateApi,
}

export_common_api!(CexPrivateApi);

#[uniffi::export(async_runtime = "tokio")]
impl CexPrivateApi {
    #[uniffi::constructor]
    pub fn new(
        api_key: String,
        api_secret: String,
        options: RestOptions,
        rate_limiter: Arc<CexRateLimiter>,
    ) -> Result<Arc<Self>, CexError> {
        Ok(Arc::new(Self {
            inner: crate::PrivateApi::new(
                api_key,
                api_secret,
                options,
                rate_limiter.inner.clone(),
            )?,
        }))
    }

    pub async fn get_my_current_fee(&self, pairs: Vec<String>) -> Result<TradingFees, CexError> {
        self.inner.get_my_current_fee(&pairs).await
    }

    pub async fn get_fee_strategy(&self) -> Result<FeeStrategy, CexError> {
        self.inner.get_fee_strategy().await
    }

    pub async fn get_my_volume(&self) -> Result<Volume, CexError> {
        self.inner.get_my_volume().await
    }

    pub async fn create_account(
        &self,
        account_id: String,
        currency: String,
    ) -> Result<CreateAccountResult, CexError> {
        self.inner.create_account(&account_id, &currency).await
    }

    pub async fn get_my_account_status(
        &self,
        request: AccountStatusRequest,
    ) -> Result<AccountStatusResult, CexError> {
        self.inner.get_my_account_status(&request).await
    }

    pub async fn account_balance(&self) -> Result<HashMap<String, AccountBalance>, CexError> {
        self.inner.account_balance().await
    }

    pub async fn orders(&self, request: OrderRequest) -> Result<Vec<OrderResult>, CexError> {
        self.inner.orders(&request).await
    }

    pub async fn order(&self, order_id: String) -> Result<Option<OrderResult>, CexError> {
        self.inner.order(&order_id).await
    }

    pub async fn orders_for_pair(
        &self,
        currency1: String,
        currency2: String,
    ) -> Result<Vec<OrderResult>, CexError> {
        self.inner.orders_for_pair(&currency1, &currency2).await
    }

    pub async fn new_order(&self, request: NewOrder) -> Result<NewOrderAnswer, CexError> {
        self.inner.new_order(&request).await
    }

    pub async fn cancel_order(&self, request: CancelOrder) -> Result<(), CexError> {
        self.inner.cancel_order(&request).await
    }

    pub async fn cancel_orders(&self) -> Result<Vec<String>, CexError> {
        self.inner.cancel_orders().await
    }

    pub async fn transaction_history(
        &self,
        request: TransactionHistoryRequest,
    ) -> Result<Vec<TransactionHistory>, CexError> {
        self.inner.transaction_history(&request).await
    }

    pub async fn funds_deposit_from_wallet(
        &self,
        request: DepositToOrWithdrawalFromWalletRequest,
    ) -> Result<DepositToOrWithdrawFromWalletResult, CexError> {
        self.inner.funds_deposit_from_wallet(&request).await
    }

    pub async fn funds_withdraw_to_wallet(
        &self,
        request: DepositToOrWithdrawalFromWalletRequest,
    ) -> Result<DepositToOrWithdrawFromWalletResult, CexError> {
        self.inner.funds_withdraw_to_wallet(&request).await
    }
}
