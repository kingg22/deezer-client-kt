import de.jensklingenberg.ktorfit.gradle.ErrorCheckingMode
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
        jvmTest.dependencies {
            implementation(libs.ktor.engine.okhttp)
        }
    }
}

ktorfit {
    errorCheckingMode = ErrorCheckingMode.ERROR
}

kover {
    reports.filters.excludes {
        inheritedFrom("io.github.kingg22.deezerSdk.api.routes.*")
    }
}

dokka {
    dokkaSourceSets.configureEach {
        skipEmptyPackages = true
        skipDeprecated = false
        reportUndocumented = true
        perPackageOption {
            // TODO find solution to include interfaces
            matchingRegex = "io\\.github\\.kingg22\\.deezerSdk\\.api\\.routes\\.*"
            suppress = true
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
