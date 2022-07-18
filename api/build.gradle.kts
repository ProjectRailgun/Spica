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
    api(kotlin("reflect"))
}

dependencies {
    api(androidx.datastore)
    api(kotlinx.coroutines.android)
    api(kotlinx.serialization.json)
    api(okhttp)
    api(omico.cloudflare.dns)
    api(omico.cryonics)
    api(protobuf.kotlin)
    api(retrofit2)
    api(retrofit2.converter.kotlinxSerialization)
    api(retrofit2.converter.scalars)
    debugApi(okhttp.interceptor.logging)
}
