plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.rbc.openweatherapi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rbc.openweatherapi"
        minSdk = 24
        targetSdk = 35
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // okHttp
    implementation(libs.okHttp3)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // glide
    implementation(libs.glide)

    // google location services
    implementation(libs.google.servics.location)

    // MPAndroidChart
    implementation(libs.philj.mpandroid.chart)

    // hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    kapt("com.google.dagger:hilt-android-compiler:2.56.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}