plugins {
  alias(libs.plugins.androidApplication)
}

android {
  namespace = "com.vroad.app.berry"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.vroad.app.berry"
    minSdk = 33
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  externalNativeBuild {
    cmake {
      path = file("src/main/cpp/CMakeLists.txt")
      version = "3.22.1"
    }
  }
  buildFeatures {
    viewBinding = true
    aidl = true
    dataBinding = true
  }
}

dependencies {
  implementation("com.squareup.retrofit2:retrofit:2.5.0")
  implementation("com.squareup.retrofit2:converter-gson:2.0.2")
  implementation("com.elvishew:xlog:1.10.1")
  compileOnly("org.projectlombok:lombok:1.18.30")
  annotationProcessor("org.projectlombok:lombok:1.18.30")
  implementation("androidx.credentials:credentials:1.3.0-alpha01")

  implementation(libs.annotation)
  implementation(libs.lifecycle.livedata.ktx)
  implementation(libs.lifecycle.viewmodel.ktx)
  implementation(libs.appcompat)
  implementation(libs.material)
  implementation(libs.constraintlayout)
  testImplementation(libs.junit)
  androidTestImplementation(libs.ext.junit)
  androidTestImplementation(libs.espresso.core)
}