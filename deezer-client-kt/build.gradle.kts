@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalAbiValidation::class)

import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.kotlinx.resources)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.poko)
}

group = "io.github.kingg22"
version = "2.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    compilerOptions {
        extraWarnings.set(true)
        allWarningsAsErrors.set(true)
    }

    abiValidation {
        enabled.set(true)
        klib {
            enabled.set(true)
        }
    }

    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
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
        commonMain {
            // indicate to KMP plugin compile the metadata of ksp
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                api(libs.bundles.ktor.client)
                api(libs.bundles.kotlinx.ecosystem)
                implementation(libs.ktorgen.annotations)
                runtimeOnly(libs.slf4j.nop)
            }
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

dependencies {
    kspCommonMainMetadata(libs.ktorgen.compiler)
}

kover {
    reports.total {
        verify {
            rule("Basic Line Coverage") {
                minBound(60, CoverageUnit.LINE)
            }
            rule("Basic Branch Coverage") {
                minBound(20, CoverageUnit.BRANCH)
            }
        }
        filters.excludes {
            annotatedBy("$group.ktorgen.core.Generated")
        }
    }
}

dokka {
    dokkaSourceSets.configureEach {
        skipEmptyPackages.set(true)
        skipDeprecated.set(false)
        reportUndocumented.set(true)
        enableJdkDocumentationLink.set(true)
        enableKotlinStdLibDocumentationLink.set(true)
        suppressGeneratedFiles.set(true)
        perPackageOption {
            matchingRegex.set("$group.deezer.client.api.objects")
            documentedVisibilities.addAll(VisibilityModifier.Internal, VisibilityModifier.Public)
        }
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
            register("ktorgen") {
                url("https://kingg22.github.io/ktorgen/")
                packageListUrl("https://kingg22.github.io/ktorgen/annotations/package-list")
            }
        }
    }
}

android {
    namespace = "$group.deezer.client"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

ktlint {
    version.set(libs.versions.ktlint.pinterest.get())
}

// Workaround kotlin multiplatform with ksp
tasks.matching { it.name != "kspCommonMainKotlinMetadata" && it.name.startsWith("ksp") }
    .configureEach {
        dependsOn("kspCommonMainKotlinMetadata")
    }

tasks.named("runKtlintCheckOverCommonMainSourceSet") {
    dependsOn("kspCommonMainKotlinMetadata")
}

tasks.named("jvmSourcesJar") {
    dependsOn("kspCommonMainKotlinMetadata")
}

tasks.named("sourcesJar") {
    dependsOn("kspCommonMainKotlinMetadata")
}

tasks.named("dokkaGeneratePublicationHtml") {
    dependsOn("compileReleaseJavaWithJavac", "releaseAssetsCopyForAGP", "compileJvmMainJava")
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "deezer-client-kt", version.toString())

    pom {
        name.set("Unofficial Deezer Client – Kotlin Multiplatform - Java library")
        description.set("A Kotlin Multiplatform library to use Deezer public API.")
        inceptionYear.set("2025")
        url.set("https://github.com/kingg22/deezer-client-kt")
        licenses {
            license {
                name.set("GNU Affero General Public License")
                url.set("https://www.gnu.org/licenses/agpl-3.0.en.html")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("kingg22")
                name.set("Rey Acosta (Kingg22)")
                url.set("https://github.com/kingg22")
            }
        }
        scm {
            url.set("https://github.com/kingg22/deezer-client-kt")
            connection.set("scm:git:git://github.com/kingg22/deezer-client-kt.git")
            developerConnection.set("scm:git:ssh://git@github.com/kingg22/deezer-client-kt.git")
        }
    }
}
