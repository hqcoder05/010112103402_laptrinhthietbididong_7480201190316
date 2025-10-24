plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)   // ✅ BẮT BUỘC với Kotlin 2.0+
    alias(libs.plugins.google.services)  // Firebase
    // alias(libs.plugins.hilt)         // (tuỳ chọn DI)
}

android {
    namespace = "vn.kotlinproject.thuchanh1"
    compileSdk = 36 // nếu chưa cài SDK 36, tạm dùng 35

    defaultConfig {
        applicationId = "vn.kotlinproject.thuchanh1"
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
    }

    // AGP 8.x nên dùng Java 17
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }

    buildFeatures { compose = true }
    // Với Kotlin 2.x KHÔNG cần set composeCompilerExtensionVersion khi đã dùng plugin compose
}

dependencies {
    // --- Compose BOM ---
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom)) // 👈 cần để resolve ui-test-junit4
    debugImplementation(platform(libs.androidx.compose.bom))       // 👈 cần để resolve ui-test-manifest
    // (tuỳ chọn) nếu có unit test dùng Compose:
    // testImplementation(platform(libs.androidx.compose.bom))

    // --- Compose libs ---
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)

    // --- Firebase ---
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.coroutines.play.services)

    // --- AndroidX cơ bản / test ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.compose.material:material-icons-extended")
}
