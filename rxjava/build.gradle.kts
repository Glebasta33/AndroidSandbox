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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    namespace = "com.github.gltrusov.rxjava"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(libs.bundles.core)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.adapter.rxjava)
    implementation(libs.rxjava)
    implementation(libs.rxjava.android)
    implementation(libs.bundles.compose)
    implementation(libs.compose.runtime.livedata)
    implementation("androidx.compose.runtime:runtime-rxjava3:1.6.8") // конвертация Observable в Compose State
    implementation(project(":core_ui"))
    implementation(project(":core_navigation"))
    implementation(project(":di_framework"))
    implementation(project(":gradle_sandbox"))
    kapt(project(":gradle_sandbox"))
    implementation(libs.markdownview.android)

}
