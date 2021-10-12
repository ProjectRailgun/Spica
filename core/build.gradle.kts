plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures {
        buildConfig = false
    }
}

dependencies {
    compileOnly(androidx.annotation.annotation)
    compileOnly(androidx.core.coreKtx)
}
