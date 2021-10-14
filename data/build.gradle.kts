plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    api(project(":data:bangumi"))
    api(project(":data:user"))
    implementation(project(":api"))
}
