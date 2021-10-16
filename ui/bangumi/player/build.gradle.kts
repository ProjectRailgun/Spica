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
    implementation("com.google.android.exoplayer:exoplayer-core:2.15.1")
    implementation("com.google.android.exoplayer:exoplayer-dash:2.15.1")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.15.1")
}
