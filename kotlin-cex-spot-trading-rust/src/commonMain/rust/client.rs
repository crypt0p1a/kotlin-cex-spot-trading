use crate::{CexError, RateLimiter};
use base64::Engine;
use base64::engine::general_purpose::STANDARD as BASE64;
use hmac::{Hmac, Mac};
use serde::Serialize;
use serde::de::DeserializeOwned;
use serde_json::Value;
use sha2::Sha256;
use std::sync::Arc;
use std::time::Duration;

const USER_AGENT: &str = "CEX.IO Spot Trading Rust Client";

/// Options of the HTTP client
#[derive(Debug, Clone, PartialEq)]
#[derive(uniffi::Record)]
pub struct RestOptions {
    /// Timeout of the connection, of the socket and of the whole request, in milliseconds
    pub timeout_ms: u64,
    /// Rejects the invalid TLS certificates
    pub reject_unauthorized: bool,
    pub host: String,
}

impl Default for RestOptions {
    fn default() -> Self {
        Self {
            timeout_ms: 30_000,
            reject_unauthorized: true,
            host: "https://trade.cex.io/api/spot".into(),
        }
    }
}

#[derive(Debug, Clone, Copy)]
pub(crate) enum Endpoint {
    Public,
    Private,
}

impl Endpoint {
    fn path(self) -> &'static str {
        match self {
            Endpoint::Public => "rest-public",
            Endpoint::Private => "rest",
        }
    }
}

pub(crate) struct Credentials {
    pub api_key: String,
    pub api_secret: String,
}

pub(crate) struct RestClient {
    http: reqwest::Client,
    host: String,
    endpoint: Endpoint,
    credentials: Option<Credentials>,
    rate_limiter: Arc<RateLimiter>,
}

impl RestClient {
    pub fn new(
        endpoint: Endpoint,
        credentials: Option<Credentials>,
        options: RestOptions,
        rate_limiter: Arc<RateLimiter>,
    ) -> Result<Self, CexError> {
        let timeout = Duration::from_millis(options.timeout_ms);
        let http = reqwest::Client::builder()
            .connect_timeout(timeout)
            .read_timeout(timeout)
            .timeout(timeout)
            .user_agent(USER_AGENT)
            .danger_accept_invalid_certs(!options.reject_unauthorized)
            .build()?;

        Ok(Self {
            http,
            host: options.host.trim_end_matches('/').to_string(),
            endpoint,
            credentials,
            rate_limiter,
        })
    }

    pub async fn call<O: DeserializeOwned>(&self, action: &str) -> Result<O, CexError> {
        self.call_with::<(), O>(action, None).await
    }

    pub async fn call_with<I: Serialize, O: DeserializeOwned>(
        &self,
        action: &str,
        params: Option<&I>,
    ) -> Result<O, CexError> {
        let action = action.trim_start_matches('/');
        let body = match params {
            Some(params) => serde_json::to_string(params).map_err(|error| CexError::Decode {
                reason: error.to_string(),
            })?,
            None => "{}".to_string(),
        };

        let path = format!("/{}/{action}", self.endpoint.path());
        let url = format!("{}{path}", self.host);

        let (status, text) = self
            .rate_limiter
            .enqueue(&path, async {
                let mut request = self
                    .http
                    .post(&url)
                    .header(reqwest::header::CONTENT_TYPE, "application/json");

                if let Some(credentials) = &self.credentials {
                    let timestamp = (crate::now_millis() as f64 / 1000.0).round() as i64;
                    let signature = sign(&credentials.api_secret, action, timestamp, &body);

                    request = request
                        .header("X-AGGR-KEY", &credentials.api_key)
                        .header("X-AGGR-TIMESTAMP", timestamp.to_string())
                        .header("X-AGGR-SIGNATURE", signature);
                }

                let response = request.body(body.clone()).send().await?;
                let status = response.status().as_u16();
                Ok((status, response.text().await?))
            })
            .await?;

        decode(status, &text)
    }
}

/// Base64 of the HMAC-SHA256 of action + timestamp + body, keyed by the API secret
pub(crate) fn sign(secret: &str, action: &str, timestamp: i64, body: &str) -> String {
    let mut mac =
        Hmac::<Sha256>::new_from_slice(secret.as_bytes()).expect("HMAC accepts any key length");
    mac.update(format!("{action}{timestamp}{body}").as_bytes());
    BASE64.encode(mac.finalize().into_bytes())
}

/// Maps an answer of CEX.IO: either `{"ok": "ok", "data": ...}`, `{"error": ...}` or the raw value
pub(crate) fn decode<O: DeserializeOwned>(status: u16, text: &str) -> Result<O, CexError> {
    if status != 200 {
        return Err(CexError::Network {
            status,
            body: text.to_string(),
        });
    }

    let decode_error = |error: serde_json::Error| CexError::Decode {
        reason: error.to_string(),
    };

    let payload = match serde_json::from_str::<Value>(text).map_err(decode_error)? {
        Value::Null => {
            return Err(CexError::Decode {
                reason: "The answer was null, this is unexpected. Please report".into(),
            });
        }
        Value::Object(mut object) => {
            if object.get("ok").and_then(Value::as_str) == Some("ok") {
                object.remove("data").ok_or_else(|| CexError::Api {
                    status,
                    body: text.to_string(),
                })?
            } else if let Some(error) = object.get("error") {
                return Err(CexError::Api {
                    status,
                    body: error
                        .as_str()
                        .map(str::to_string)
                        .unwrap_or_else(|| error.to_string()),
                });
            } else {
                Value::Object(object)
            }
        }
        other => other,
    };

    serde_json::from_value(payload).map_err(decode_error)
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::collections::HashMap;

    #[test]
    fn signs_action_timestamp_and_body() {
        // base64(HMAC-SHA256("secret", "get_my_volume1700000000{}"))
        assert_eq!(
            sign("secret", "get_my_volume", 1_700_000_000, "{}"),
            "FJSQiXG7rcVd6sK8brOdL3KZgBbZII5TVX12AWydFSw="
        );
    }

    #[test]
    fn unwraps_the_data_of_ok_answers() {
        let value: HashMap<String, i32> = decode(200, r#"{"ok":"ok","data":{"a":1}}"#).unwrap();
        assert_eq!(value["a"], 1);
    }

    #[test]
    fn keeps_answers_without_envelope() {
        let value: Vec<i32> = decode(200, "[1,2,3]").unwrap();
        assert_eq!(value, vec![1, 2, 3]);
    }

    #[test]
    fn maps_the_errors() {
        assert_eq!(
            decode::<Value>(200, r#"{"error":"Rate limit exceeded"}"#),
            Err(CexError::Api {
                status: 200,
                body: "Rate limit exceeded".into()
            })
        );
        assert_eq!(
            decode::<Value>(200, r#"{"ok":"ok"}"#),
            Err(CexError::Api {
                status: 200,
                body: r#"{"ok":"ok"}"#.into()
            })
        );
        assert_eq!(
            decode::<Value>(503, "unavailable"),
            Err(CexError::Network {
                status: 503,
                body: "unavailable".into()
            })
        );
        assert!(matches!(
            decode::<Value>(200, "null"),
            Err(CexError::Decode { .. })
        ));
    }
}
