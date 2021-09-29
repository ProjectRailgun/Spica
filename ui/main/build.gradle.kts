plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures {
        buildConfig = false
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = androidx.versions.compose.get()
    }
}

dependencies {
    compileOnly(accompanist.insets)
    compileOnly(accompanist.insetsUi)
    compileOnly(accompanist.swipeRefresh)
    compileOnly(accompanist.systemUiController)
    compileOnly(androidx.activity.activityCompose)
    compileOnly(androidx.activity.activityKtx)
    compileOnly(androidx.annotation.annotation)
    compileOnly(androidx.appcompat)
    compileOnly(androidx.compose.foundation)
    compileOnly(androidx.compose.material)
    compileOnly(androidx.compose.runtime)
    compileOnly(androidx.compose.ui)
    compileOnly(androidx.compose.uiToolingPreview)
    compileOnly(androidx.navigation.compose)
    compileOnly(androidx.navigation.runtimeKtx)
    compileOnly(material.material)
    compileOnly(project(":ui:common"))
    compileOnly(project(":ui:home"))
    compileOnly(project(":ui:login"))
    compileOnly(project(":ui:splash"))
    compileOnly(project(":ui:theme"))
    debugCompileOnly(androidx.compose.uiTooling)
}
