plugins {
    kotlin("jvm") version "1.8.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.1.0"
}

group = "austins.computer.tbdutils"
version = "2.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")

    implementation("cloud.commandframework:cloud-core:1.8.3")
    implementation("cloud.commandframework:cloud-paper:1.8.3")
    implementation("cloud.commandframework:cloud-kotlin-extensions:1.8.3")
    implementation("org.reflections:reflections:0.10.2")
}

tasks {
    runServer {
        minecraftVersion("1.20.1")
    }
    shadowJar {
        val shadePkg = "computer.austins.tbdutils.shade"

        relocate("org.reflections", "$shadePkg.reflections")
        relocate("cloud.commandframework", "$shadePkg.cloud")
    }
}

kotlin {
    jvmToolchain(17)
}
