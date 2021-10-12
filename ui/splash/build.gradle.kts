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
    compileOnly(project(":ui:common"))
    compileOnly(project(":ui:home"))
    compileOnly(project(":ui:login"))
}
