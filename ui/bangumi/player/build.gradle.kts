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
    implementation(exoplayer2.core)
    implementation(exoplayer2.dash)
    implementation(exoplayer2.ui)
}
