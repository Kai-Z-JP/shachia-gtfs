plugins {
    kotlin("multiplatform") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
    id("maven-publish")
}

group = "jp.kaiz"
version = "0.0.3"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js {
        binaries.executable()
        browser {
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                implementation("app.softwork:kotlinx-serialization-csv:0.0.18")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(npm("fflate", "0.8.2"))
                implementation(npm("papaparse", "5.4.1"))
            }
        }
    }
}

kotlin {
    jvmToolchain(21)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Kai-Z-JP/shachia-gtfs")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}