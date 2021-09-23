plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

dependencies {
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    testImplementation("junit:junit:4.13.2")

    api("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.retrofit2:adapter-rxjava2:2.2.0")
    api("com.google.code.gson:gson:2.8.7")

    api("com.github.bumptech.glide:glide:3.8.0")
    api("org.greenrobot:eventbus:3.0.0")

    api("io.reactivex.rxjava2:rxandroid:2.0.1")
    api("io.reactivex.rxjava2:rxjava:2.1.16")
    api("com.trello.rxlifecycle2:rxlifecycle:2.0.1")
    api("com.trello.rxlifecycle2:rxlifecycle-android:2.0.1")

    api("com.google.firebase:firebase-appindexing:20.0.0")

    api("com.google.android.exoplayer:exoplayer-core:2.10.8")
    api("com.kaopiz:kprogresshud:1.2.0")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    api("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    api("me.omico.cloudflare.api:cloudflare-dns:1.0.0-SNAPSHOT")

    compileOnly(project(":api"))
}
