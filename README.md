# Welcome to CEX.IO Spot Trading Library for Kotlin Multiplatform 👋
![Version](https://img.shields.io/badge/version-0.0.1-blue.svg?cacheSeconds=2592000)
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
cex = "0.0.1"


[libraries]
cex = { module = "eu.codlab:kotlin-cex-spot-trading", version.ref = "cex" }
```

**Old gradle**

```
implementation("eu.codlab:kotlin-cex-spot-trading:0.0.1")
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
