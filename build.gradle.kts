val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.9"
}

version = "0.0.1"

application {
    mainClass.set("ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor Server
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.10.1")
    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-gson-jvm")

    // Ktor Client
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-client-okhttp")
    implementation("io.ktor:ktor-client-okhttp-jvm")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Test dependencies
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
