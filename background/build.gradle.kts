plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
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
        viewBinding = true
    }
    namespace = "com.github.gltrusov.background"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = 24
    }
}

dependencies {

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.bundles.core)
    implementation(libs.corotines.core)
    implementation(libs.corotines.android)
    implementation(libs.work.manager)
    implementation("com.github.billthefarmer:MarkdownView:v1.11")
    implementation(project(":core_ui"))
    implementation(project(":core_navigation"))
    implementation(project(":di_framework"))
    implementation(project(":gradle_sandbox"))
    kapt(project(":gradle_sandbox"))
}
