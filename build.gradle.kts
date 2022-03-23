plugins {
    kotlin("jvm") version "1.6.10"
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "moe.neat"
version = "1.5-SNAPSHOT"

repositories {
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://maven.maxhenkel.de/repository/public") }
    maven { url = uri("https://nexus.scarsz.me/content/groups/public/") }
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.2.19")
    compileOnly("com.discordsrv:discordsrv:1.25.0")
    implementation("com.github.ReflxctionDev:SimpleHypixelAPI:1.0.9-BETA")
    implementation("org.reflections:reflections:0.10.2")
    implementation("cloud.commandframework:cloud-paper:1.6.2")
    implementation("cloud.commandframework:cloud-annotations:1.6.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}