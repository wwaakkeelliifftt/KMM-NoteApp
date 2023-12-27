import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.notekmm.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.notekmm.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.ui:ui-tooling:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.compose.foundation:foundation:1.5.4")
//    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")


    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
//    configurations.getByName("kapt").dependencies.add(
//        DefaultExternalModuleDependency(
//          "com.google.dagger",
//            "hilt-android-compiler",
//            "2.44"
//        )
//    )

    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
//    configurations.getByName("kapt").dependencies.add(
//        DefaultExternalModuleDependency(
//            "androidx.hilt",
//            "hilt-compiler",
//            "1.1.0"
//        )
//    )
}