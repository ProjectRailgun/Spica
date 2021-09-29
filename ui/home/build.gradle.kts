plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures {
        buildConfig = false
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = androidx.versions.compose.get()
    }
}

dependencies {
    compileOnly(androidx.compose.runtime)
    compileOnly(androidx.navigation.runtimeKtx)
    compileOnly(project(":ui:common"))
}
