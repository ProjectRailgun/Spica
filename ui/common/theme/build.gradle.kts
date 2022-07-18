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
    compileOnly(projects.ui.common.resources)
}

dependencies {
    api(material)
    api(androidx.compose.material)
}
