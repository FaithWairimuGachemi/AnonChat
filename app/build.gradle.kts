import org.gradle.api.JavaVersion
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.anonchat"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.anonchat"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        //packaging options
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    dependencies {
        // AndroidX and Material Design libraries
        implementation(libs.appcompat)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        implementation(libs.material)

        // Importing Firebase Bill of Materials (BOM)
        // This platform dependency manages the versions of all other Firebase libraries.
        implementation(platform("com.google.firebase:firebase-bom:33.1.0"))

        //Add Firebase dependencies
        implementation("com.google.firebase:firebase-auth")
        implementation("com.google.firebase:firebase-storage")
        implementation("com.google.firebase:firebase-firestore")

        // FirebaseUI version  specified.
        implementation("com.firebaseui:firebase-ui-firestore:8.0.2")

        // Testing dependencies
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    }
}
