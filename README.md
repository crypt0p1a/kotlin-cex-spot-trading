# Welcome to CEX.IO Spot Trading Library for Kotlin Multiplatform 👋
![Version](https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000)
[![Documentation](https://img.shields.io/badge/documentation-yes-brightgreen.svg)](https://github.com/crypt0p1a/kotlin-cex-spot-trading#readme)
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://github.com/crypt0p1a/kotlin-cex-spot-trading/graphs/commit-activity)
[![License: GPLv3](https://img.shields.io/github/license/crypt0p1a/trade)](https://github.com/crypt0p1a/kotlin-cex-spot-trading/blob/master/LICENSE)
[![Twitter: codlab](https://img.shields.io/twitter/follow/codlab.svg?style=social)](https://twitter.com/codlab)

> CEX.IO Spot Trading Kotlin Multiplatform Library

### 🏠 [Homepage](https://github.com/crypt0p1a/kotlin-cex-spot-trading#readme)

## Install

### In your projects

**libs.versions.toml**

```
[versions]
cex = "1.0.0"


[libraries]
cex = { module = "eu.codlab:kotlin-cex-spot-trading", version.ref = "cex" }
```

**Old gradle**

```
implementation("eu.codlab:kotlin-cex-spot-trading:1.0.0")
```

### Locally for dev

```sh
./gradlew publishToMavenLocal
```

## Usage

```kotlin
val privateInstance = PrivateApi(
    "yourPrivateApiKey",
    "yourApiSecret"
)

val publicInstance = PublicApi()
```

Both will expose the endpoints documented in the [cex's documentation](https://trade.cex.io/docs/#rest)

Share an `ApiConfiguration` between the instances: its `RateLimitQueue` limits the calls made to
CEX.IO. Call `shutdown()` to release an instance.

The errors are thrown as `RestClientNetworkException` (HTTP status other than 200),
`RestClientPairException` (error answered by CEX.IO), `RestClientTransportException` (request not
sent or answer not read) or `SerializationException` (unexpected answer).

### Platforms

`jvm`, `android`, `iosX64`, `iosArm64`, `iosSimulatorArm64`, `macosArm64`, `mingwX64`,
`linuxArm64`, `linuxX64`.

### Implementation

The calls are made by the Rust crate of the `kotlin-cex-spot-trading-rust` module, exported to
Kotlin Multiplatform with UniFFI: the library converts its models from and to the generated ones,
which stay internal. See [its README](kotlin-cex-spot-trading-rust/README.md) for the build
requirements.

## Build

The build relies on [gradle-tools](https://github.com/the-inkwell/gradle-tools), at the commit
`GRADLE_EXTENDED_VERSION_USED` of `gradle.properties`: Gradle 9.7, AGP 9.3 and Kotlin 2.4.20, as
required by the UniFFI plugin of the Rust module.

## Run tests

```sh
./gradlew check
```

## Author

👤 **codlab**

* Twitter: [@codlab](https://twitter.com/codlab)
* Github: [@crypt0p1a](https://github.com/crypt0p1a)

## 🤝 Contributing

Contributions, issues and feature requests are welcome!

Feel free to check [issues page](https://github.com/crypt0p1a/trade/issues). You can also take a look at the [contributing guide](https://github.com/crypt0p1a/trade/blob/master/CONTRIBUTING.md).

## Show your support

Give a ⭐️ if this project helped you!


## 📝 License

Copyright © 2025 [crypt0p1a](https://github.com/crypt0p1a).

This project is [MIT](https://github.com/crypt0p1a/trade/blob/master/LICENSE) licensed.
