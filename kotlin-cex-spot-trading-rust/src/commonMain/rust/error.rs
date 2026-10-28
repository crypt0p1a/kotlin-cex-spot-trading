/// Errors of the calls to CEX.IO
#[derive(Debug, Clone, PartialEq, thiserror::Error)]
#[derive(uniffi::Error)]
pub enum CexError {
    /// The server answered with an HTTP status other than 200
    #[error("HTTP {status}: {body}")]
    Network { status: u16, body: String },

    /// The server answered with an error payload, or without the expected data
    #[error("CEX.IO error (HTTP {status}): {body}")]
    Api { status: u16, body: String },

    /// The request couldn't be sent or its answer couldn't be read
    #[error("Transport error: {reason}")]
    Transport { reason: String },

    /// The answer doesn't match the expected model
    #[error("Unexpected answer: {reason}")]
    Decode { reason: String },
}

impl From<reqwest::Error> for CexError {
    fn from(error: reqwest::Error) -> Self {
        CexError::Transport {
            reason: error.to_string(),
        }
    }
}
