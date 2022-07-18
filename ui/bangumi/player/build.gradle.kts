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
    compileOnly(projects.ui.common)
}

dependencies {
    implementation(exoplayer.core)
    implementation(exoplayer.dash)
    implementation(exoplayer.ui)
}
