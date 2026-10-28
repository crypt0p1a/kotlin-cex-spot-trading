plugins {
    alias(additionals.plugins.kotlin.multiplatform)
    alias(additionals.plugins.android.kotlin.multiplatform.library)
    alias(additionals.plugins.kotlin.serialization)
    id("publication")
    id("iosSimulatorConfiguration")
    id("jvmCompat")
}

kotlin {
    android {
        namespace = "eu.codlab.cex.spot.trading"
        compileSdk = additionals.versions.compileSdkVersion.get().toInt()
        minSdk = additionals.versions.minSdkVersion.get().toInt()
    }

    jvm()

    js(IR) {
        browser()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    macosArm64()
    macosX64()

    mingwX64()
    linuxArm64()
    linuxX64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.http.client)
                implementation(libs.crypto)
                implementation(libs.korio.klock)
                implementation(additionals.kotlinx.serialization.json)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(additionals.kotlinx.coroutines.core)
                implementation(additionals.kotlinx.coroutines.test)
                implementation(project(":test-configuration"))
            }
        }
    }
}
