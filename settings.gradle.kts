@file:Suppress("UnstableApiUsage")

rootProject.name = "Spica"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal {
            content {
                includeGroupByRegex("com.diffplug.spotless.*")
                includeGroupByRegex("com.github.ben-manes.*")
                includeGroupByRegex("com.gradle.*")
                includeGroupByRegex("org.gradle.*")
                includeGroupByRegex("org.jetbrains.kotlin.*")
            }
        }
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
    id("com.gradle.enterprise") version "3.11.1"
    id("me.omico.gradm") version "2.5.0-SNAPSHOT"
}

buildscript {
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(!gradle.startParameter.isOffline)
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
