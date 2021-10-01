plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = androidx.versions.compose.get()
    }
}

dependencies {
    compileOnly(androidx.compose.runtime)
    compileOnly(androidx.navigation.compose)
    compileOnly(androidx.navigation.runtimeKtx)
    api(project(":ui:resources"))
}
