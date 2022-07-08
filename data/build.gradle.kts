plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    api(projects.data.bangumi)
    api(projects.data.user)
    implementation(projects.api)
}
