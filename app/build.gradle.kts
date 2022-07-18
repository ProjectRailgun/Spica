import me.omico.age.dsl.withBuildType

plugins {
    id("com.android.application")
    id("com.google.firebase.crashlytics") apply false
    id("com.google.gms.google-services") apply false
    kotlin("android")
}

withBuildType("release") {
    apply(plugin = "com.google.gms.google-services")
    apply(plugin = "com.google.firebase.crashlytics")
}

android {
    defaultConfig {
        applicationId = "co.railgun.spica"
        versionCode = 7
        versionName = "0.9.2"
    }
    flavorDimensions += "build"
    productFlavors {
        create("default") {
            dimension = "build"
        }
        create("preview") {
            dimension = "build"
            applicationIdSuffix = ".preview"
            versionName = "1.0.0-alpha01"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.findByName("release")
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.ui)
    implementation(platform(firebase.bom))
    implementation(firebase.crashlyticsKtx)
}
