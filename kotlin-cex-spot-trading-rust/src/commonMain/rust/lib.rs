//! CEX.IO Spot Trading REST client
//!
//! Rust port of the `kotlin-cex-spot-trading` Kotlin Multiplatform library: [`PublicApi`] and
//! [`PrivateApi`] expose the endpoints documented in <https://trade.cex.io/docs/#rest>, sharing a
//! [`RateLimiter`] which keeps the calls under the limits of the platform.

uniffi::setup_scaffolding!("cex_spot_trading");

mod client;
mod error;
mod lenient;
pub mod models;
mod rate_limit;

pub use client::RestOptions;
pub use error::CexError;
pub use rate_limit::{Logger, RateLimiter};

pub(crate) fn now_millis() -> i64 {
    std::time::SystemTime::now()
        .duration_since(std::time::UNIX_EPOCH)
        .map(|it| it.as_millis() as i64)
        .unwrap_or_default()
}
