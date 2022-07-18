plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures {
        buildConfig = false
        compose = true
    }
}

dependencies {
    compileOnly(androidx.annotation)
    compileOnly(androidx.compose.runtime)
    compileOnly(androidx.core.ktx)
    compileOnly(androidx.lifecycle.viewmodel.compose)
    compileOnly(androidx.lifecycle.viewmodel.ktx)
}
