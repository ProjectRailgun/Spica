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
    implementation(exoplayer2.core)
    implementation(exoplayer2.dash)
    implementation(exoplayer2.ui)
}
