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
    compileOnly(kotlinx.coroutines.core)
    compileOnly(retrofit2.retrofit)
    compileOnly(project(":api"))
}
