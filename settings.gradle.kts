rootProject.name = "Spica"

pluginManagement {
    val versions = object {
        val agePlugin = "1.0.0-SNAPSHOT"
        val androidGradlePlugin = "7.1.0-alpha13"
        val kotlinPlugin = "1.5.31"
    }
    plugins {
        id("com.android.application") version versions.androidGradlePlugin
        id("com.android.library") version versions.androidGradlePlugin
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
createVersionCatalog("kotlinx")
createVersionCatalog("material")
createVersionCatalog("okhttp4")
createVersionCatalog("omico")
createVersionCatalog("protobuf3")
createVersionCatalog("retrofit2")

//include(":common")
//include(":mobile")

include(":app")

include(":api")

include(":data")
include(":data:user")

include(":ui")
include(":ui:common")
include(":ui:compose-preview")
include(":ui:home")
include(":ui:login")
include(":ui:main")
include(":ui:resources")
include(":ui:splash")
include(":ui:theme")

fun createVersionCatalog(name: String) =
    dependencyResolutionManagement.versionCatalogs.create(name) {
        from(files("gradle/common-version-catalogs/$name.versions.toml"))
    }
