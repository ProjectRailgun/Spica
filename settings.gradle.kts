rootProject.name = "Spica"

pluginManagement {
    val versions = object {
        val agePlugin = "1.0.0-SNAPSHOT"
        val androidGradlePlugin = "7.0.2"
        val kotlinPlugin = "1.5.30"
    }
    plugins {
        id("com.android.application") version versions.androidGradlePlugin
        id("com.android.library") version versions.androidGradlePlugin
        id("me.omico.age") version versions.agePlugin
        kotlin("android") version versions.kotlinPlugin
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
    }
}

include(":common")
include(":mobile")
