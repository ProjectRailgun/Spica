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
    api(androidx.compose.uiToolingPreview)
    compileOnly(project(":ui:common"))
    debugApi(androidx.compose.uiTooling)
}
