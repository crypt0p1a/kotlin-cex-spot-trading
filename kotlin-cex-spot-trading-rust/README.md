# kotlin-cex-spot-trading-rust

Rust port of `kotlin-cex-spot-trading`, exposed to Kotlin Multiplatform through
[UniFFI](https://mozilla.github.io/uniffi-rs/) and the
[UniFFI Kotlin Multiplatform plugin](https://github.com/UbiqueInnovation/uniffi-kotlin-multiplatform-bindings)
(`ch.ubique.uniffi.plugin`), like the `shared-rust` module of Fetoly.

| Folder                | Content                                                                        |
|-----------------------|--------------------------------------------------------------------------------|
| `src/commonMain/rust` | `cex-spot-trading` crate: client, models, rate limiter and its UniFFI facade (`ffi.rs`) |
| `src/commonMain/kotlin` | Kotlin API on the generated bindings: `PublicApi`, `PrivateApi`, `ApiConfiguration` |
| `src/commonTest/rust` | Rust tests calling the real CEX.IO API (ignored by default)                    |
| `src/commonTest/kotlin` | Kotlin tests on the Rust library, calling the real CEX.IO API               |

The crate is a member of the Cargo workspace at the root of the repository. The plugin builds it
for each Kotlin target and generates the bindings in `eu.codlab.cex.spot.trading.rust.bindings`.

## Targets

`jvm` (the host library is bundled in the jar), `android`, `iosX64`, `iosArm64`,
`iosSimulatorArm64`, `macosArm64`, `mingwX64`, `linuxArm64`, `linuxX64`. There is no `js`: the
Rust code is called through JNA / cinterop. There is no `macosX64` either: neither the UniFFI
plugin nor its runtime support it.

## Usage

```kotlin
val configuration = ApiConfiguration() // share it: CEX.IO limits the calls per client

val publicApi = PublicApi(apiConfiguration = configuration)
publicApi.tickers("BTC-USD")

val privateApi = PrivateApi("apiKey", "apiSecret", apiConfiguration = configuration)
privateApi.orders("BTC" to "USD")
```

The failures are thrown as `CexException` (`Network`, `Api`, `Transport`, `Decode`). Call
`close()` / `shutdown()` to release the native objects.

```rust
let api = PublicApi::new(RestOptions::default(), Arc::new(RateLimiter::default()))?;
api.server_time().await?;
```

## Build and test

Requires rustup with the targets of the platforms to build (`aarch64-linux-android`,
`armv7-linux-androideabi`, `i686-linux-android`, `x86_64-linux-android`, `aarch64-apple-ios`,
`aarch64-apple-ios-sim`, `x86_64-apple-ios`, `aarch64-unknown-linux-gnu`, `x86_64-unknown-linux-gnu`,
`x86_64-pc-windows-gnu`), the C cross-compilers of the Linux and Windows targets
(`aarch64-linux-gnu-gcc`, `x86_64-linux-gnu-gcc`, `x86_64-w64-mingw32-gcc`, used by `ring`) and
the NDK `ndkVersion` of the gradle-tools catalog. The first build
compiles the UniFFI generator (~6 to 10 minutes), kept in `build/uniffi/bindgen`.

```sh
cargo test                       # Rust unit tests
cargo test -- --include-ignored  # + calls to the real CEX.IO API
./gradlew :kotlin-cex-spot-trading-rust:jvmTest :kotlin-cex-spot-trading-rust:macosArm64Test
```

The Rust library is built in debug unless `-PreleaseBuild=true` is given to Gradle.

## Differences with the former Kotlin implementation

`kotlin-cex-spot-trading` calls this crate, which changed the behaviour of its calls:

- `Ticker`: every field is optional, CEX.IO currently only sends bestBid, bestAsk, last, volume,
  volumeUSD and priceChangePercentage
- empty pair / currency lists are omitted (CEX.IO rejects `"pairs": []`), so `pairsInfo()` returns
  all the pairs
- `OrderRequest.clientOrderId` is sent
- the leading `/` of the actions is removed, from the URL and from the signed message
- `NewOrder.price` is optional (market orders)

The generated models differ from the ones of `kotlin-cex-spot-trading`, which converts them:

- `side` of `OrderRequest` is an `OrderSide`, `OrderResult.expireTime` is a string
- dates are UTC milliseconds (`fromMs`, `toMs`) instead of klock `DateTime`
- `Blockchain.type` is `blockchainType`, `StrategyConfig.default` is `defaultFees`
