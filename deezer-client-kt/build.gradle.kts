@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalAbiValidation::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinxKover)
    alias(libs.plugins.kotlinxResources)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.ktorfit)
}

group = "io.github.kingg22"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        extraWarnings.set(true)
        allWarningsAsErrors.set(true)
    }

    abiValidation {
        // until api client for java consumers is created
        enabled.set(false)
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

    // tier 2
    linuxX64()
    linuxArm64()
    // tier 3
    mingwX64()

    applyDefaultHierarchyTemplate {
        common {
            group("androidAndJvm") {
                withAndroidTarget()
                withJvm()
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.ktor.client)
            api(libs.bundles.kotlinx.ecosystem)
            implementation(libs.ktorfit.light)
            runtimeOnly(libs.slf4j.nop)
        }
        commonTest.dependencies {
            implementation(libs.bundles.testing)
            implementation(libs.bundles.testing.ktor)
            implementation(libs.kermit)
        }
        jvmTest.dependencies {
            implementation(libs.ktor.client.engine.cio)
        }
    }
}

kover {
    reports.filters.excludes {
        classes(
            "$group.deezer.client.api.routes.*Impl",
            "$group.deezer.client.api.routes.*ImplKt",
            "$group.deezer.client.api.routes.*Impl.kt",
            "$group.deezer.client.api.routes.*Provider",
        )
        inheritedFrom("$group.deezer.client.api.routes.*Routes")
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
        }
    }
}

android {
    namespace = "$group.deezer.client"
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
