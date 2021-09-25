import me.omico.age.dsl.withBuildType

plugins {
    id("com.android.application")
    kotlin("android")
}

withBuildType("release") {
    apply(plugin = "com.google.gms.google-services")
    apply(plugin = "com.google.firebase.crashlytics")
}

android {
    defaultConfig {
        applicationId = "co.railgun.spica"
        versionCode = 7
        versionName = "0.9.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            // TODO Disable before refactoring the entire project.
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "consumer-rules.pro",
            )
            signingConfig = signingConfigs.findByName("release")
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation(project(":common"))
    androidTestImplementation("junit:junit:4.13.2")

    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    implementation(platform("com.google.firebase:firebase-bom:28.4.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-dynamic-links")
    implementation("com.google.firebase:firebase-messaging")

    implementation("com.youth.banner:banner:1.4.10")
    implementation("com.github.Yalantis:Context-Menu.Android:1.0.8")

    implementation(project(":api"))
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-beta01")

    implementation("androidx.datastore:datastore:1.0.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.18.0")
    implementation("me.omico.cryonics:cryonics:1.0.0-SNAPSHOT")
}
