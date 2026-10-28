//! CEX.IO Spot Trading REST client
//!
//! Rust port of the `kotlin-cex-spot-trading` Kotlin Multiplatform library: [`PublicApi`] and
//! [`PrivateApi`] expose the endpoints documented in <https://trade.cex.io/docs/#rest>, sharing a
//! [`RateLimiter`] which keeps the calls under the limits of the platform.

uniffi::setup_scaffolding!("cex_spot_trading");

mod error;
mod rate_limit;

pub use error::CexError;
pub use rate_limit::{Logger, RateLimiter};
