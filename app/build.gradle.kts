plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.github.gltrusov"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.gltrusov"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    defaultConfig {
        javaCompileOptions.annotationProcessorOptions.arguments["dagger.hilt.disableModulesHaveInstallInCheck"] =
            "true"
    }
    kapt {
        arguments {
            arg("dagger.formatGeneratedSource", "disabled")
            arg("dagger.fastInit", "enabled")
            arg("dagger.gradle.incremental", "enabled")
        }
    }
}

dependencies {
    implementation(libs.bundles.core)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.retrofit)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.kotlin.reflect)
    implementation(libs.drawablepainter)

    implementation(project(":di_framework"))
    implementation(project(":core_navigation"))
    implementation(project(":test_feature"))
    implementation(project(":compose"))
}