plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization")
}

android {
    buildTypes {
        release {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    compileOnly("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    compileOnly("com.squareup.retrofit2:retrofit:2.9.0")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    compileOnly("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    compileOnly("me.omico.cloudflare.api:cloudflare-dns:1.0.0-SNAPSHOT")

    compileOnly("com.squareup.retrofit2:converter-scalars:2.9.0")
    debugImplementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    compileOnly("androidx.datastore:datastore:1.0.0")
    compileOnly("com.google.protobuf:protobuf-kotlin:3.18.0")
    compileOnly("me.omico.cryonics:cryonics:1.0.0-SNAPSHOT")
}
