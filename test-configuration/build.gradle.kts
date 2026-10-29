import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(additionals.plugins.kotlin.multiplatform)
    alias(additionals.plugins.android.kotlin.multiplatform.library)
    alias(additionals.plugins.kotlin.serialization)
    alias(additionals.plugins.multiplatform.buildkonfig)
    id("jvmCompat")
}

kotlin {
    android {
        namespace = "eu.codlab.configuration"
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
                implementation(additionals.kotlinx.serialization.json)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(additionals.kotlinx.coroutines.core)
                implementation(additionals.kotlinx.coroutines.test)
            }
        }
    }
}

buildkonfig {
    packageName = "eu.codlab.configuration"

    defaultConfigs {
        listOf(
            "apiKey",
            "apiSecret",
            "clientId",
        ).forEach {
            buildConfigField(
                FieldSpec.Type.STRING,
                it,
                rootProject.extra[it] as String? ?: "",
                nullable = false,
                const = true
            )
        }
    }
}
