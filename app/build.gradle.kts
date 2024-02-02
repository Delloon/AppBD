plugins {
    id("com.android.application")
    id("com.google.gms.google-services") version "4.3.15" apply false
//добавление, возможно нужно будет поменять версию на   "4.4.0"
}

android {
    namespace = "com.delloon.appbd"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.delloon.appbd"
        minSdk = 29
        targetSdk = 33
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.10.1")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.firebaseui:firebase-ui-database:8.0.0")
    implementation("com.firebaseui:firebase-ui-firestore:8.0.0")
    implementation("com.firebaseui:firebase-ui-auth:8.0.0")
    implementation("com.firebaseui:firebase-ui-storage:8.0.0")
    implementation ("com.google.firebase:firebase-database:23.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}