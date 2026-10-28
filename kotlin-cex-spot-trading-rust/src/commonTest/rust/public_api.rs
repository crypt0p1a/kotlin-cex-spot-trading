//! Calls the real CEX.IO public API: `cargo test -p cex-spot-trading -- --ignored`

use cex_spot_trading::models::{CandleResolution, CandlesFromPair, DataType};
use cex_spot_trading::{PublicApi, RateLimiter, RestOptions};
use std::sync::{Arc, LazyLock};

/// Shared like in an application: CEX.IO limits the calls per client
static RATE_LIMITER: LazyLock<Arc<RateLimiter>> = LazyLock::new(Default::default);

fn now_millis() -> i64 {
    std::time::SystemTime::now()
        .duration_since(std::time::UNIX_EPOCH)
        .unwrap()
        .as_millis() as i64
}

fn api() -> PublicApi {
    PublicApi::new(RestOptions::default(), RATE_LIMITER.clone()).unwrap()
}

#[tokio::test]
#[ignore = "calls the real CEX.IO API"]
async fn server_time() {
    let time = api().server_time().await.unwrap();
    assert!(time.timestamp > 0.0);
    assert!(!time.iso_date.is_empty());
}

#[tokio::test]
#[ignore = "calls the real CEX.IO API"]
async fn tickers_order_book_and_candles() {
    let api = api();

    let tickers = api.tickers(&["BTC-USD".into()]).await.unwrap();
    assert!(tickers["BTC-USD"].last.unwrap_or_default() > 0.0);

    let book = api.order_book("BTC", "USD").await.unwrap();
    assert_eq!(book.currency1, "BTC");
    assert!(!book.bids.is_empty());

    let candles = api
        .candles_from_pair(&CandlesFromPair {
            pair: "BTC-USD".into(),
            from_ms: None,
            // CEX.IO requires fromISO or toISO
            to_ms: Some(now_millis()),
            limit: Some(5),
            data_type: DataType::BestAsk,
            resolution: CandleResolution::Res1h,
        })
        .await
        .unwrap();
    assert!(!candles.is_empty());
}

#[tokio::test]
#[ignore = "calls the real CEX.IO API"]
async fn pairs_currencies_and_processing() {
    let api = api();

    assert!(!api.pairs_info(&[]).await.unwrap().is_empty());
    assert!(!api.currency_infos().await.unwrap().is_empty());
    assert!(
        api.processing_info(&["BTC".into()])
            .await
            .unwrap()
            .contains_key("BTC")
    );
}
