plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "moe.neat"
version = "1.7-SNAPSHOT"

repositories {
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://maven.maxhenkel.de/repository/public") }
    maven { url = uri("https://nexus.scarsz.me/content/groups/public/") }
    maven {
        name = "m2-dv8tion"
        url = uri("https://m2.dv8tion.net/releases")
    }
    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    maven { url = uri("https://repo.hypixel.net/repository/Hypixel/") }
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.2.22")
    compileOnly("com.discordsrv:discordsrv:1.26.0")
    implementation("net.hypixel:hypixel-api-transport-apache:4.2.1")
    implementation("org.reflections:reflections:0.10.2")

    implementation("cloud.commandframework:cloud-paper:1.7.1")
    implementation("cloud.commandframework:cloud-annotations:1.7.1")
    implementation("de.tr7zw:item-nbt-api-plugin:2.10.0")
    implementation("com.sksamuel.scrimage:scrimage-core:4.0.32")
}

tasks {
    shadowJar {
        val shadePkg = "moe.neat.shade"

        relocate("net.hypixel", "$shadePkg.hypixel")
        relocate("org.reflections", "$shadePkg.reflections")
        relocate("cloud.commandframework", "$shadePkg.cloud")
        relocate("de.tr7zw", "$shadePkg.tr7zw")
        relocate("com.sksamuel.scrimage", "$shadePkg.scrimage")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}