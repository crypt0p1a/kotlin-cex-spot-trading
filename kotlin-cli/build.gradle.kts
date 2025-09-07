import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    alias(additionals.plugins.kotlin.jvm)
    id("jvmCompat")
}

val mainClassInManifest = "eu.codlab.cex.ApplicationKt"

group = "eu.codlab.cex"
version = "0.0.1"

repositories {
    mavenCentral()
    google()
    mavenLocal()
}

application {
    mainClass.set(mainClassInManifest)

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    manifest {
        attributes["Main-Class"] = mainClassInManifest
    }
}

dependencies {
    api(project(":kotlin-cex-spot-trading"))
    implementation(additionals.kotlinx.coroutines.core)
    implementation(additionals.kotlinx.coroutines.jvm)
    implementation(additionals.multiplatform.file.access)
    implementation(libs.kotlinx.cli)
}
