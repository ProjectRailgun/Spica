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
    api(projects.ui.bangumi.detail)
    api(projects.ui.bangumi.player)
    api(projects.ui.common)
    api(projects.ui.home)
    api(projects.ui.login)
    api(projects.ui.main)
    api(projects.ui.splash)
}
