import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(additionals.plugins.kotlin.multiplatform)
    alias(additionals.plugins.android.kotlin.multiplatform.library)
    alias(additionals.plugins.ubique.uniffi)
}

cargo {
    ndkVersion = additionals.versions.ndkVersion
}

uniffi {
    generateFromLibrary()
}

// The Apple targets can only be built on macOS
tasks.matching { (it.name.contains("Ios") || it.name.contains("Macos")) && !OperatingSystem.current().isMacOsX }
    .configureEach { enabled = false }

kotlin {
    applyDefaultHierarchyTemplate()

    // the UniFFI runtime is built for Java 17, its inline functions need the same target
    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    android {
        namespace = "eu.codlab.cex.spot.trading.rust"
        compileSdk = additionals.versions.compileSdkVersion.get().toInt()
        minSdk = additionals.versions.minSdkVersion.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }

        optimization {
            consumerKeepRules.publish = true
            consumerKeepRules.file("consumer-rules.pro")
        }
    }

    iosArm64()
    iosSimulatorArm64()
    macosArm64()

    // the uniffi bindings hold unused expressions, flagged since Kotlin 2.4
    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions.freeCompilerArgs.add("-Xwarning-level=UNUSED_EXPRESSION:disabled")
            }
        }
    }

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(additionals.kotlinx.coroutines.test)
        }
    }
}
