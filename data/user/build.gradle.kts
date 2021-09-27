plugins {
    id("com.android.library")
    kotlin("android")
}

dependencies {
    compileOnly(kotlinx.coroutines.core)
    compileOnly(retrofit2.retrofit)
    compileOnly(project(":api"))
}
