import org.gradle.plugins.ide.idea.model.IdeaModel

plugins {
    alias(additionals.plugins.kotlin.multiplatform)
    alias(additionals.plugins.android.library)
    alias(additionals.plugins.jetbrains.compose)
    alias(additionals.plugins.kotlin.serialization)
    id("publication")
    id("jvmCompat")
    id("idea")
    idea
}

idea {
    module {
        sourceDirs.add(File("./src/commonMain/kotlin"))
    }
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
                implementation(additionals.kotlinx.serialization.json)
            }
        }
        val commonTest by getting
    }
}

android {
    namespace = "eu.codlab.cex.spot.trading"
}
