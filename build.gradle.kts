import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.9.20"
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.9.20"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    val ktor_version: String by project

    dependencies {
        implementation("io.ktor:ktor-client-core:$ktor_version")
        implementation("io.ktor:ktor-client-cio:$ktor_version")
        implementation("io.ktor:ktor-client-websockets:$ktor_version")

        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
        implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

        implementation("com.google.code.gson:gson:2.10.1") // Replace with the desired version
        // SLF4J API
        implementation("org.slf4j:slf4j-api:1.7.32")

        // Logback Logging Implementation
        implementation("ch.qos.logback:logback-classic:1.2.5")


    }
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Websocket-tungstenite-ktor"
            packageVersion = "1.0.0"
        }
    }
}

