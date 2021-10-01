import me.omico.age.dsl.withBuildType

plugins {
    id("com.android.application")
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
    composeOptions {
        kotlinCompilerExtensionVersion = androidx.versions.compose.get()
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":ui"))
    implementation(kotlinx.coroutines.core)
    implementation(kotlinx.serialization.json)
    implementation(okhttp4.okhttp)
    implementation(omico.cloudflareApi.dns)
    implementation(omico.cryonics.cryonics)
    implementation(protobuf3.protobufKotlin)
    implementation(retrofit2.converter.kotlinxSerialization)
    implementation(retrofit2.converter.scalars)
    implementation(retrofit2.retrofit)
    debugImplementation(okhttp4.interceptor.logging)
}
