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
    compileOnly(androidx.annotation.annotation)
    compileOnly(androidx.compose.runtime)
    compileOnly(androidx.core.coreKtx)
    compileOnly(androidx.lifecycle.viewmodelCompose)
    compileOnly(androidx.lifecycle.viewmodelKtx)
}
