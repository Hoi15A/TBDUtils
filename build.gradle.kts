plugins {
    kotlin("jvm") version "1.6.10"
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
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
    compileOnly("de.maxhenkel.voicechat:voicechat-api:2.2.19")
    compileOnly("com.discordsrv:discordsrv:1.25.1")
    implementation("com.github.ReflxctionDev:SimpleHypixelAPI:1.0.9-BETA")
    implementation("org.reflections:reflections:0.10.2")
    implementation("cloud.commandframework:cloud-paper:1.7.0")
    implementation("cloud.commandframework:cloud-annotations:1.7.0")
    implementation("de.tr7zw:item-nbt-api-plugin:2.10.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}