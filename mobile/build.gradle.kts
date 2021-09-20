plugins {
    id("com.android.application")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    kotlin("android")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "co.railgun.spica"
        minSdk = 26
        targetSdk = 30
        versionCode = 7
        versionName = "0.9.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        release {
//            signingConfig signingConfigs.release
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-core")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-dynamic-links")
    implementation("com.google.firebase:firebase-messaging")

    implementation("com.youth.banner:banner:1.4.10")
    implementation("com.github.Yalantis:Context-Menu.Android:1.0.8")
}
