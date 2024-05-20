plugins {
    alias(additionals.plugins.kotlin.multiplatform)
    alias(additionals.plugins.android.library)
    alias(additionals.plugins.kotlin.serialization)
    id("publication")
    id("jvmCompat")
    id("idea")
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
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
        val commonMain by getting {
            kotlin {
                srcDirs.add(File("src/commonMain/kotlin"))
            }
            dependencies {
                implementation(libs.http.client)
                implementation(libs.crypto)
                implementation(additionals.kotlinx.serialization.json)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "eu.codlab.cex.spot.trading"
}
