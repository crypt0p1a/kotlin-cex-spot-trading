//! Models of the requests and answers, serialized like the JSON of CEX.IO (camelCase)

mod account;
mod market;
mod orders;

pub use account::*;
pub use market::*;
pub use orders::*;

use serde::{Deserialize, Serialize};

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
pub enum OrderSide {
    #[serde(rename = "BUY")]
    Buy,
    #[serde(rename = "SELL")]
    Sell,
}

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash, Default, Serialize, Deserialize)]
#[derive(uniffi::Enum)]
pub enum SortOrder {
    #[default]
    #[serde(rename = "DESC")]
    Desc,
    #[serde(rename = "ASC")]
    Asc,
}

/// No pairs means all of them: CEX.IO rejects an empty list but accepts a missing one
#[derive(Debug, Clone, PartialEq, Serialize)]
pub(crate) struct Pairs<'a> {
    #[serde(skip_serializing_if = "<[String]>::is_empty")]
    pub pairs: &'a [String],
}

/// No currencies means all of them
#[derive(Debug, Clone, PartialEq, Serialize)]
pub(crate) struct Currencies<'a> {
    #[serde(skip_serializing_if = "<[String]>::is_empty")]
    pub currencies: &'a [String],
}
