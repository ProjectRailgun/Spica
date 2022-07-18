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
    api(projects.core)
    api(projects.data)
    api(projects.ui.common.resources)
    api(projects.ui.common.theme)
}

dependencies {
    api(accompanist.insets)
    api(accompanist.insetsUi)
    api(accompanist.swipeRefresh)
    api(accompanist.systemUiController)
    api(androidx.activity.compose)
    api(androidx.activity.ktx)
    api(androidx.annotation)
    api(androidx.appcompat)
    api(androidx.compose.animation)
    api(androidx.compose.foundation)
    api(androidx.compose.material.icons.core)
    api(androidx.compose.material.icons.extended)
    api(androidx.compose.runtime)
    api(androidx.compose.ui)
    api(androidx.compose.ui.tooling.preview)
    api(androidx.compose.ui.util)
    api(androidx.core.ktx)
    api(androidx.lifecycle.runtime.ktx)
    api(androidx.navigation.compose)
    api(androidx.navigation.runtime.ktx)
    api(coil)
    api(coil.compose)
    api(kotlinx.coroutines.android)
    debugApi(androidx.compose.ui.tooling)
}
