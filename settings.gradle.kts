@file:Suppress("UnstableApiUsage")

import me.omico.gradm.configs
import me.omico.gradm.gradm

rootProject.name = "Spica"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
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
        mavenCentral()
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
        maven(url = "https://androidx.dev/storage/compose-compiler/repository")
    }
}

plugins {
    id("me.omico.age.settings") version "1.0.0-SNAPSHOT"
    id("me.omico.gradm") version "2.2.0-SNAPSHOT"
}

buildscript {
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
    }
}

gradm {
    configs {
        format = true
    }
}

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
include(":ui:common:resources")
include(":ui:common:theme")
include(":ui:home")
include(":ui:login")
include(":ui:main")
include(":ui:splash")
