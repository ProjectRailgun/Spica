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
    api(project(":ui:common"))
    api(project(":ui:home"))
    api(project(":ui:login"))
    api(project(":ui:splash"))
}
