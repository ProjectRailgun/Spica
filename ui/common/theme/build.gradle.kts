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
    compileOnly(accompanist.systemUiController)
    compileOnly(androidx.compose.material3)
    compileOnly(androidx.core.splashScreen)
    compileOnly(material)
}
