@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.github.gltrusov.core.ui"
    compileSdk = 34

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    api(libs.material)
    api(libs.androidx.constraintlayout)
    implementation(libs.bundles.core)
    implementation(libs.bundles.compose)
    implementation(libs.markdownview.android)
}