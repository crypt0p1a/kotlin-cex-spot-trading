use crate::client::{Credentials, Endpoint, RestClient};
use crate::common::common_api;
use crate::models::{
    AccountBalance, AccountStatusRequest, AccountStatusResult, CancelOrder, CancelOrders,
    CreateAccountRequest, CreateAccountResult, DepositToOrWithdrawFromWalletResult,
    DepositToOrWithdrawalFromWalletRequest, FeeStrategy, NewOrder, NewOrderAnswer, OrderRequest,
    OrderResult, Pairs, TradingFees, TransactionHistory, TransactionHistoryRequest, Volume,
};
use crate::{CexError, RateLimiter, RestOptions};
use serde::de::IgnoredAny;
use std::collections::HashMap;
use std::sync::Arc;

/// Private API of CEX.IO Spot Trading, signed with the API key and secret
pub struct PrivateApi {
    client: RestClient,
}

common_api!(PrivateApi);

impl PrivateApi {
    pub fn new(
        api_key: String,
        api_secret: String,
        options: RestOptions,
        rate_limiter: Arc<RateLimiter>,
    ) -> Result<Self, CexError> {
        let credentials = Credentials {
            api_key,
            api_secret,
        };

        Ok(Self {
            client: RestClient::new(Endpoint::Private, Some(credentials), options, rate_limiter)?,
        })
    }

    /// Current fees, depending on the 30d volume and the day of the week
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-current-fee
    pub async fn get_my_current_fee(&self, pairs: &[String]) -> Result<TradingFees, CexError> {
        self.client
            .call_with("get_my_current_fee", Some(&Pairs { pairs }))
            .await
    }

    /// All the fee options which could be applied to the Client
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-fee-strategy
    pub async fn get_fee_strategy(&self) -> Result<FeeStrategy, CexError> {
        self.client.call("get_fee_strategy").await
    }

    /// Trading volume of the last 30 days, in USD
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-volume
    pub async fn get_my_volume(&self) -> Result<Volume, CexError> {
        self.client.call("get_my_volume").await
    }

    /// Creates a sub-account, up to 5 accounts including the main one
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-create-account
    pub async fn create_account(
        &self,
        account_id: &str,
        currency: &str,
    ) -> Result<CreateAccountResult, CexError> {
        let request = CreateAccountRequest {
            account_id,
            currency,
        };
        self.client
            .call_with("do_create_account", Some(&request))
            .await
    }

    /// Balances per sub-account and currency, with the amounts locked in open orders
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-account-status-v3
    pub async fn get_my_account_status(
        &self,
        request: &AccountStatusRequest,
    ) -> Result<AccountStatusResult, CexError> {
        self.client
            .call_with("get_my_account_status_v3", Some(request))
            .await
    }

    /// Balances of the CEX.IO Wallet, keyed by currency
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-wallet-balance
    pub async fn account_balance(&self) -> Result<HashMap<String, AccountBalance>, CexError> {
        self.client.call("get_my_wallet_balance").await
    }

    /// Orders matching the request
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-orders
    pub async fn orders(&self, request: &OrderRequest) -> Result<Vec<OrderResult>, CexError> {
        self.client.call_with("get_my_orders", Some(request)).await
    }

    /// The order with this CEX.IO identifier
    pub async fn order(&self, order_id: &str) -> Result<Option<OrderResult>, CexError> {
        let request = OrderRequest {
            order_id: Some(order_id.to_string()),
            ..Default::default()
        };

        Ok(self
            .orders(&request)
            .await?
            .into_iter()
            .find(|order| order.order_id == order_id))
    }

    /// The open orders of a pair
    pub async fn orders_for_pair(
        &self,
        currency1: &str,
        currency2: &str,
    ) -> Result<Vec<OrderResult>, CexError> {
        let request = OrderRequest {
            pair: Some(format!("{currency1}-{currency2}")),
            ..Default::default()
        };
        self.orders(&request).await
    }

    /// Places an order on a Spot Trading sub-account. Prefer setting a client_order_id: the orders
    /// sent again with the same one are only placed once
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-new-order
    pub async fn new_order(&self, request: &NewOrder) -> Result<NewOrderAnswer, CexError> {
        let request = NewOrder {
            timestamp: request.timestamp.or_else(|| Some(crate::now_millis())),
            ..request.clone()
        };
        self.client
            .call_with("do_my_new_order", Some(&request))
            .await
    }

    /// Cancels an order, fails with an error otherwise
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-cancel-order
    pub async fn cancel_order(&self, request: &CancelOrder) -> Result<(), CexError> {
        let request = CancelOrder {
            timestamp: request.timestamp.or_else(|| Some(crate::now_millis())),
            ..request.clone()
        };
        self.client
            .call_with::<_, IgnoredAny>("do_cancel_my_order", Some(&request))
            .await
            .map(|_| ())
    }

    /// Cancels all the open orders, returns their client order ids
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-cancel-all-orders
    pub async fn cancel_orders(&self) -> Result<Vec<String>, CexError> {
        self.client
            .call::<CancelOrders>("do_cancel_all_orders")
            .await
            .map(|answer| answer.client_order_ids)
    }

    /// Deposits, withdrawals, internal transfers, commissions or trades
    ///
    /// https://trade.cex.io/docs/#rest-private-api-calls-transaction-history
    pub async fn transaction_history(
        &self,
        request: &TransactionHistoryRequest,
    ) -> Result<Vec<TransactionHistory>, CexError> {
        self.client
            .call_with("get_my_transaction_history", Some(request))
            .await
    }

    /// Moves funds from the CEX.IO Wallet to a Spot Trading sub-account
    pub async fn funds_deposit_from_wallet(
        &self,
        request: &DepositToOrWithdrawalFromWalletRequest,
    ) -> Result<DepositToOrWithdrawFromWalletResult, CexError> {
        self.client
            .call_with("do_deposit_funds_from_wallet", Some(request))
            .await
    }

    /// Moves funds from a Spot Trading sub-account to the CEX.IO Wallet
    pub async fn funds_withdraw_to_wallet(
        &self,
        request: &DepositToOrWithdrawalFromWalletRequest,
    ) -> Result<DepositToOrWithdrawFromWalletResult, CexError> {
        self.client
            .call_with("do_withdrawal_funds_to_wallet", Some(request))
            .await
    }
}
