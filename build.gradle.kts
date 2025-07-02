
plugins {
    alias(libs.plugins.kotlin.jvm)
    kotlin("plugin.serialization") version "1.9.10"
    alias(libs.plugins.ktor)
}

group = "com.fuse.code.order.processing"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
    implementation("io.ktor:ktor-server-config-yaml:2.3.4")

    // H2 Database
    implementation("com.h2database:h2:2.2.224")

    // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:0.45.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.45.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.45.0")

    // JDBC
    implementation("org.jetbrains.exposed:exposed-java-time:0.45.0")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("org.apache.kafka:kafka-clients:3.9.1")


}
