import me.omico.age.dsl.configureAndroidCommon
import me.omico.age.dsl.configureAppSigningConfigsForRelease
import me.omico.age.dsl.kotlinCompile

plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("me.omico.age")
    kotlin("android") apply false
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")
    }
}

allprojects {
    configureAppSigningConfigsForRelease()
    configureAndroidCommon {
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
