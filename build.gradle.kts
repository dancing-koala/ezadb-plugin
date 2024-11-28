import java.util.*

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.21"
    id("org.jetbrains.intellij.platform") version "2.1.0"
}

group = "com.cobr.quickadb"
version = "1.0.0"

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
        val localProps = Properties()

        File(rootDir, "local.properties").takeIf { it.exists() }
            ?.inputStream()
            ?.use(localProps::load)

        certificateChainFile.set(file("cert/chain.crt"))
        privateKeyFile.set(file("cert/private.pem"))
        password = localProps.getProperty("privateKeyPassword")
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
