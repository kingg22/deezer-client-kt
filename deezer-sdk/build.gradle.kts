import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinPluginSerialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinKover)
    alias(libs.plugins.kotlinxResources)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
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
    linuxX64()
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlinx.ecosystem)
            implementation(libs.bundles.ktorfit)
            implementation(libs.kermit)
        }
        commonTest.dependencies {
            implementation(libs.bundles.testing)
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
