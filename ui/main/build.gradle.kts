plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = androidx.versions.compose.get()
    }
}

dependencies {
    implementation(accompanist.insets)
    implementation(accompanist.insetsUi)
    implementation(accompanist.swipeRefresh)
    implementation(accompanist.systemUiController)
    implementation(androidx.activity.activityCompose)
    implementation(androidx.activity.activityKtx)
    implementation(androidx.annotation.annotation)
    implementation(androidx.appcompat)
    implementation(androidx.compose.foundation)
    implementation(androidx.compose.material)
    implementation(androidx.compose.runtime)
    implementation(androidx.compose.ui)
    implementation(androidx.compose.uiToolingPreview)
    implementation(androidx.navigation.compose)
    implementation(androidx.navigation.runtimeKtx)
    implementation(material.material)
    implementation(project(":ui:common"))
    implementation(project(":ui:home"))
    implementation(project(":ui:login"))
    implementation(project(":ui:splash"))
    implementation(project(":ui:theme"))
    debugImplementation(androidx.compose.uiTooling)
}
