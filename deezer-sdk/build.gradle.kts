import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinPluginSerialization)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinxKover)
    alias(libs.plugins.kotlinxResources)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.ktlint)
}

group = "io.github.kingg22"
version = "3.0.0"

kotlin {
    compilerOptions {
        extraWarnings.set(true)
        allWarningsAsErrors.set(true)
    }

    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.kotlinx.ecosystem)
            implementation(libs.bundles.ktorfit)
        }
        commonTest.dependencies {
            implementation(libs.bundles.testing)
            implementation(libs.kermit)
        }
    }
}

kover {
    reports.filters.excludes {
        classes("$group.deezerSdk.*.routes.*ImplKt", "$group.deezerSdk.*.routes.*Provider")
        inheritedFrom("$group.deezerSdk.*.routes.*")
    }
}

dokka {
    dokkaSourceSets.configureEach {
        skipEmptyPackages = true
        skipDeprecated = false
        reportUndocumented = true
        enableJdkDocumentationLink = true
        enableKotlinStdLibDocumentationLink = true
        suppressedFiles.from(layout.buildDirectory.dir("generated"))
        externalDocumentationLinks {
            register("kotlinx.coroutines") {
                url("https://kotlinlang.org/api/kotlinx.coroutines/")
            }
            register("kotlinx.serialization") {
                url("https://kotlinlang.org/api/kotlinx.serialization/")
            }
            register("kotlinx-datetime") {
                url("https://kotlinlang.org/api/kotlinx-datetime/")
                packageListUrl("https://kotlinlang.org/api/kotlinx-datetime/kotlinx-datetime/package-list")
            }
            register("ktor-client") {
                url("https://api.ktor.io/ktor-client/")
                packageListUrl("https://api.ktor.io/package-list")
            }
            register("kermit-logger") {
                url("https://kermit.touchlab.co/htmlMultiModule/")
            }
        }
        perPackageOption {
            matchingRegex = """io\.github\.kingg22\.deezerSdk\.gw\.objects"""
            reportUndocumented = false
        }
        perPackageOption {
            matchingRegex = """io\.github\.kingg22\.deezerSdk\.gw\.objects\.media"""
            reportUndocumented = false
        }
    }
}

android {
    namespace = "$group.deezerSdk"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

ktlint {
    version.set(libs.versions.ktlint.pinterest.get())
}

tasks.named("runKtlintCheckOverCommonMainSourceSet") {
    dependsOn("kspCommonMainKotlinMetadata")
}
