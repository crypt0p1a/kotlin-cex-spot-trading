# The UniFFI bindings call the Rust library through JNA, which relies on reflection: Library
# interfaces are proxied by method name, Structure fields are read by name and Callback
# implementations are looked up through their "callback" method.
# The JNA aar doesn't ship any consumer rules, so they are declared here.
-keep class com.sun.jna.** { *; }
-keep class * implements com.sun.jna.** { *; }
-dontwarn java.awt.**

# Generated bindings (package_name in uniffi.toml)
-keep class eu.codlab.cex.spot.trading.rust.bindings.** { *; }
