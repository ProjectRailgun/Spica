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
    api(androidx.core.splashScreen)
    api(material)
    compileOnly(accompanist.systemUiController)
    compileOnly(androidx.compose.material3)
}
