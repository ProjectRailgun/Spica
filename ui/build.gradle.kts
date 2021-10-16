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
    api(project(":ui:bangumi:detail"))
    api(project(":ui:bangumi:player"))
    api(project(":ui:common"))
    api(project(":ui:home"))
    api(project(":ui:login"))
    api(project(":ui:main"))
    api(project(":ui:splash"))
}
