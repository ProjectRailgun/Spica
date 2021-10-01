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
    compileOnly(accompanist.insets)
    compileOnly(androidx.compose.foundation)
    compileOnly(androidx.compose.material)
    compileOnly(androidx.compose.runtime)
    compileOnly(androidx.navigation.compose)
    compileOnly(project(":ui:common"))
    compileOnly(project(":ui:theme"))
    debugApi(androidx.compose.uiTooling)
}
