plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.21"
    id("org.jetbrains.intellij.platform") version "2.1.0"
}

group = "com.cobr.quickadb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.1")
        plugin("org.jetbrains.android", "241.14494.127")

        pluginVerifier()
        zipSigner()

        instrumentationTools()
    }

    implementation("com.michael-bull.kotlin-result:kotlin-result:2.0.0")
}

intellijPlatform {

    pluginConfiguration {

        ideaVersion {
            sinceBuild = "241"
            untilBuild = "242.*"
        }
    }

    signing {
        certificateChain = System.getenv("CERTIFICATE_CHAIN")
        privateKey = System.getenv("PRIVATE_KEY")
        password = System.getenv("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        host = ""
        token = "7hR4nD0mT0k3n_8f2eG"
        channels = listOf("default")
        ideServices = false
        hidden = false
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}
