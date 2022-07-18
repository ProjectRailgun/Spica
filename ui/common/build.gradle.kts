plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures {
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
    api(androidx.activity.activityCompose)
    api(androidx.activity.activityKtx)
    api(androidx.annotation.annotation)
    api(androidx.appcompat)
    api(androidx.compose.animation)
    api(androidx.compose.foundation)
    api(androidx.compose.material)
    api(androidx.compose.materialIconsCore)
    api(androidx.compose.materialIconsExtended)
    api(androidx.compose.runtime)
    api(androidx.compose.runtimeSaveable)
    api(androidx.compose.ui)
    api(androidx.compose.uiToolingPreview)
    api(androidx.compose.uiUtil)
    api(androidx.core.coreKtx)
    api(androidx.lifecycle.runtimeKtx)
    api(androidx.navigation.compose)
    api(androidx.navigation.runtimeKtx)
    api(coil.coil)
    api(coil.coilCompose)
    api(kotlinx.coroutines.core)
    api(material.material)
    debugApi(androidx.compose.uiTooling)
}
