import java.util.*

val localProperties by lazy {
    Properties().apply {
        File(rootDir, "local.properties").takeIf { it.exists() }
            ?.inputStream()
            ?.use(this::load)
    }
}

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.21"
    id("org.jetbrains.intellij.platform") version "2.1.0"
}

group = "com.cobr.quickadb"
version = "1.0.3"

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
            untilBuild = "243.*"
        }
    }

    signing {
        certificateChainFile.set(file("cert/chain.crt"))
        privateKeyFile.set(file("cert/private.pem"))
        password = localProperties.getProperty("privateKeyPassword")
    }

    publishing {
        token = localProperties.getProperty("intellijPlatformPublishingToken")
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
