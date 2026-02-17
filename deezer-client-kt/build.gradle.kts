import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.jetbrains.dokka.gradle.engine.parameters.KotlinPlatform
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.android.multiplatform.library)
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
description = "A Kotlin Multiplatform library to use Deezer public API."
version = "3.2.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    compilerOptions {
        extraWarnings.set(true)
        allWarningsAsErrors.set(true)
        optIn.addAll(
            "io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient",
            "io.github.kingg22.deezer.client.utils.InternalDeezerClient",
        )
        freeCompilerArgs.addAll("-Xexpect-actual-classes")
        apiVersion.set(KotlinVersion.KOTLIN_2_1)
        languageVersion.set(apiVersion)
    }

    @OptIn(ExperimentalAbiValidation::class)
    abiValidation {
        enabled.set(true)
        klib {
            enabled.set(true)
        }
    }

    androidLibrary {
        namespace = "$group.deezer.client"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
            jvmDefault.set(JvmDefaultMode.NO_COMPATIBILITY)
        }
        withHostTest {}
        @Suppress("UnstableApiUsage")
        with(optimization.consumerKeepRules) {
            publish = true
            files.add(project.file("consumer-rules.pro"))
        }
    }

    jvm {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
            jvmDefault.set(JvmDefaultMode.NO_COMPATIBILITY)
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("androidAndJvm") {
                withAndroidTarget()
                // The default `withAndroidTarget` doesn't include the target created by the new KMP Android plugin.
                withCompilations { it.target is KotlinMultiplatformAndroidLibraryTarget }
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
    // kspCommonMainMetadata(libs.ktorgen.compiler)
    add("kspAndroid", libs.ktorgen.compiler)
    add("kspJvm", libs.ktorgen.compiler)
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
        enableAndroidDocumentationLink.set(false)
        jdkVersion.set(8)
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
                packageListUrl("https://kingg22.github.io/ktorgen/api/annotations/package-list")
            }
        }

        // adds source links that lead to this repository, allowing readers
        // to easily find source code for inspected declarations
        sourceLink {
            localDirectory.set(file("src"))
            remoteUrl("https://github.com/kingg22/deezer-client-kt/tree/main/deezer-client-kt/src")
            remoteLineSuffix.set("#L")
        }
    }
    dokkaSourceSets.named("androidAndJvmMain") {
        // This source set is designed for Java access
        displayName.set("Java")
        // In this source set have a trick with internal and published API to only java access ;)
        documentedVisibilities.addAll(VisibilityModifier.Internal, VisibilityModifier.Public)
        // Is android jvm, but we want to see Javadocs
        // I recommend using kotlin in android
        analysisPlatform.set(KotlinPlatform.AndroidJVM)
    }
}

ktlint {
    version.set(libs.versions.ktlint.pinterest)
}

poko {
    pokoAnnotation.set("io/github/kingg22/deezer/client/utils/DeezerApiPoko")
}

tasks.check {
    dependsOn(tasks.checkLegacyAbi)
}

/* Workaround kotlin multiplatform with ksp
tasks.matching { it.name != "kspCommonMainKotlinMetadata" && it.name.startsWith("ksp") }
    .configureEach {
        dependsOn("kspCommonMainKotlinMetadata")
    }

tasks.runKtlintCheckOverCommonMainSourceSet {
    dependsOn("kspCommonMainKotlinMetadata")
}

tasks.named("jvmSourcesJar") {
    dependsOn("kspCommonMainKotlinMetadata")
}

tasks.sourcesJar {
    dependsOn("kspCommonMainKotlinMetadata")
}
 */

tasks.dokkaGeneratePublicationHtml {
    dependsOn("compileJvmMainJava")
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "deezer-client-kt", version.toString())

    pom {
        name.set("Unofficial Deezer Client – Kotlin Multiplatform - Java library")
        description.set(project.description)
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
