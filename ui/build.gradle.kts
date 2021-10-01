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
    api(material.material)
    api(project(":ui:common"))
    api(project(":ui:home"))
    api(project(":ui:login"))
    api(project(":ui:main"))
    api(project(":ui:resources"))
    api(project(":ui:splash"))
    api(project(":ui:theme"))
}
