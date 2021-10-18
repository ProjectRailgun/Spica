rootProject.name = "Spica"

pluginManagement {
    val versions = object {
        val agePlugin = "1.0.0-SNAPSHOT"
        val androidGradlePlugin = "7.2.0-alpha02"
        val gradleVersionsPlugin = "0.39.0"
        val kotlinPlugin = "1.5.31"
        val spotlessPlugin = "5.17.0"
    }
    plugins {
        id("com.android.application") version versions.androidGradlePlugin
        id("com.android.library") version versions.androidGradlePlugin
        id("com.diffplug.spotless") version versions.spotlessPlugin
        id("com.github.ben-manes.versions") version versions.gradleVersionsPlugin
        id("me.omico.age") version versions.agePlugin
        kotlin("android") version versions.kotlinPlugin
        kotlin("plugin.serialization") version versions.kotlinPlugin
    }
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}

enableFeaturePreview("VERSION_CATALOGS")

createVersionCatalog("accompanist")
createVersionCatalog("androidx")
createVersionCatalog("coil")
createVersionCatalog("exoplayer2")
createVersionCatalog("kotlinx")
createVersionCatalog("material")
createVersionCatalog("okhttp4")
createVersionCatalog("omico")
createVersionCatalog("protobuf3")
createVersionCatalog("retrofit2")

// include(":common")
// include(":mobile")

include(":api")
include(":app")
include(":core")
include(":data")
include(":data:bangumi")
include(":data:user")
include(":ui")
include(":ui:bangumi:detail")
include(":ui:bangumi:player")
include(":ui:common")
include(":ui:common:theme")
include(":ui:home")
include(":ui:login")
include(":ui:main")
include(":ui:splash")

fun createVersionCatalog(name: String) =
    dependencyResolutionManagement.versionCatalogs.create(name) {
        from(files("gradle/common-version-catalogs/$name.versions.toml"))
    }
