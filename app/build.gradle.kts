import org.jetbrains.kotlin.gradle.utils.IMPLEMENTATION

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // hilt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

    // for kotlin 2.x up vertsion 추가
    id("org.jetbrains.kotlin.plugin.compose")

    // room
    id("androidx.room")
    id("com.google.devtools.ksp")

    // multipart/from-dat
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.example.marketing"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.marketing"
        minSdk = 32
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.media3.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Icon
    implementation(libs.androidx.material.icons.extended)

    // Hilt
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0") // integrate Hilt and Navigation


    // ktor
    implementation("io.ktor:ktor-client-core:3.0.1")
    implementation("io.ktor:ktor-client-cio:3.0.1") // cio engine
    implementation("io.ktor:ktor-client-content-negotiation:3.0.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.1")
    implementation("io.ktor:ktor-client-resources:3.0.1")
    implementation("io.ktor:ktor-serialization-gson:3.0.1") // parsing
    implementation("io.ktor:ktor-client-logging:3.0.1") // logging


    // viewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // Data Source
    implementation("androidx.datastore:datastore:1.1.3")
    implementation("androidx.datastore:datastore-preferences:1.1.3")

    // navigation
    implementation("androidx.navigation:navigation-compose:2.8.9") // 최신 버전

    // coil for img
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.ktor:ktor-client-android:3.0.1")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")


    // jwt token storage (jetpack security)
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // Google Maps
    implementation("com.google.maps.android:maps-compose:4.4.1")
    implementation("com.google.maps.android:maps-ktx:5.0.0")

    // room
    val room_version = "2.7.1"
    implementation("androidx.room:room-runtime:$room_version")
    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:$room_version")

    // json for multipart/from-data
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

}