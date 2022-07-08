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
    compileOnly(projects.ui.bangumi.detail)
    compileOnly(projects.ui.bangumi.player)
    compileOnly(projects.ui.common)
    compileOnly(projects.ui.home)
    compileOnly(projects.ui.login)
    compileOnly(projects.ui.splash)
}
