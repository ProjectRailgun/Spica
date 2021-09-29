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
    compileOnly(androidx.compose.material)
    compileOnly(androidx.navigation.compose)
    compileOnly(androidx.navigation.runtimeKtx)
    compileOnly(kotlinx.coroutines.core)
    compileOnly(project(":data:user"))
    compileOnly(project(":ui:common"))
    compileOnly(project(":ui:home"))
    compileOnly(project(":ui:login"))
}
