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
    api(androidx.datastore.datastore)
    api(kotlinx.coroutines.core)
    api(kotlinx.serialization.json)
    api(okhttp4.interceptor.logging)
    api(okhttp4.okhttp)
    api(omico.cloudflareApi.dns)
    api(omico.cryonics.cryonics)
    api(protobuf3.protobufKotlin)
    api(retrofit2.converter.kotlinxSerialization)
    api(retrofit2.converter.scalars)
    api(retrofit2.retrofit)
}
