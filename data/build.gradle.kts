plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    api(project(":api"))
    api(project(":data:user"))
}
