import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost
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
    alias(libs.plugins.changelog)
    alias(libs.plugins.vanniktechMavenPublish)
}

group = "io.github.kingg22"
version = "1.0.0"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm()
    linuxX64()

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

kover {
    reports.filters.excludes {
        classes("$group.deezerSdk.*.routes.*ImplKt", "$group.deezerSdk.*.routes.*Provider")
        inheritedFrom("$group.deezerSdk.*.routes.*")
    }
}

tasks.named("formatKotlinCommonMain") {
    mustRunAfter(tasks.named("kspCommonMainKotlinMetadata"))
}

tasks.named("lintKotlinCommonMain") {
    mustRunAfter(tasks.named("kspCommonMainKotlinMetadata"))
}

dokka {
    dokkaSourceSets.configureEach {
        skipEmptyPackages = true
        skipDeprecated = false
        reportUndocumented = true
        perPackageOption {
            // TODO find solution to include interfaces
            matchingRegex = "io\\.github\\.kingg22\\.deezerSdk\\..*\\.routes\\.*"
            suppress = true
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

changelog {
    version.set(project.version.toString())
}

ktorfit {
    kotlinVersion = "2.1.0"
}

mavenPublishing {
    configure(KotlinMultiplatform(JavadocJar.Dokka("dokkaGenerate"), true, listOf("debug", "release")))
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
    mavenPublishing {
        coordinates("$group", "deezerSdk", project.version.toString())

        pom {
            name.set("Unofficial Deezer SDK")
            description.set("An unofficial Deezer SDK for Kotlin KMP and Java")
            inceptionYear.set("2025")
            url.set("https://github.com/Kingg22/deemix-kt")
            licenses {
                license {
                    name.set("GNU General Public License v3.0")
                    url.set("https://github.com/Kingg22/deemix-kt/blob/main/LICENSE")
                    distribution.set("https://www.gnu.org/licenses/gpl-3.0.html#license-text")
                }
            }
            developers {
                developer {
                    id.set("Kingg22")
                    name.set("Rey")
                    url.set("https://github.com/Kingg22/")
                }
            }
            scm {
                url.set("https://github.com/Kingg22/deemix-kt")
                connection.set("scm:git:git://github.com/Kingg22/deemix-kt.git")
                developerConnection.set("scm:git:ssh://git@github.com/Kingg22/deemix-kt.git")
            }
        }
    }
}
