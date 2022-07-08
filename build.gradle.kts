import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import me.omico.age.dsl.configureAndroidCommon
import me.omico.age.dsl.configureAppSigningConfigsForRelease
import me.omico.age.dsl.kotlinCompile
import me.omico.age.spotless.androidXml
import me.omico.age.spotless.configureSpotless
import me.omico.age.spotless.intelliJIDEARunConfiguration
import me.omico.age.spotless.kotlin
import me.omico.age.spotless.kotlinGradle

plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions")
    id("me.omico.age.project")
    id("me.omico.age.spotless")
    kotlin("android") apply false
}

allprojects {
    configureDependencyUpdates()
    configureSpotless {
        androidXml()
        intelliJIDEARunConfiguration()
        kotlin()
        kotlinGradle()
    }
    configureAppSigningConfigsForRelease()
    configureAndroidCommon {
        namespace = androidNamespace
        compileSdk = 31
        buildToolsVersion = "31.0.0"
        defaultConfig {
            minSdk = 26
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        composeOptions {
            kotlinCompilerExtensionVersion = androidx.versions.compose.get()
        }
    }
    kotlinCompile {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}

fun Project.configureDependencyUpdates() {
    configurations.all {
        resolutionStrategy.force("org.jacoco:org.jacoco.ant:0.8.7")
    }
    apply(plugin = "com.github.ben-manes.versions")
    tasks.withType<DependencyUpdatesTask> {
        rejectVersionIf {
            when {
                stableList.contains(candidate.group) -> isNonStable(candidate.version)
                else -> false
            }
        }
    }
}

val stableList = listOf(
    "ch.qos.logback",
    "com.squareup.okhttp3",
    "org.jetbrains.kotlin",
    "org.jetbrains.kotlin.android",
    "org.jetbrains.kotlin.plugin.serialization",
    "org.jetbrains.kotlinx",
)

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

val Project.androidNamespace
    get() = path.replace(":", ".")
        .let { if (it == ".app") "" else it.replace("-", ".") }
        .let { "co.railgun.spica$it" }
