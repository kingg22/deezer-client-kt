import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinPluginSerialization)
}

group = "io.github.kingg22"
version = "0.0.1"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(libs.logback)
        }
        jvmTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }
        commonMain.dependencies {
            implementation(libs.bundles.kotlinx.ecosystem)
            implementation(libs.bundles.ktor.client)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.engine.okhttp)
        }
    }
}

android {
    namespace = "io.github.kingg22.deezerSdk"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
