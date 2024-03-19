plugins {
  alias(libs.plugins.androidApplication)
}

android {
  namespace = "com.vroad.app.berry"
  compileSdk = 34
  sourceSets["main"].jniLibs.srcDir("libs")

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
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.elvishew:xlog:1.11.0")
  implementation("org.projectlombok:lombok:1.18.30")
  implementation(libs.navigation.fragment)
  implementation(libs.navigation.ui)
  implementation(libs.activity)
  annotationProcessor("org.projectlombok:lombok:1.18.30")
  implementation("androidx.credentials:credentials:1.3.0-alpha01")
  implementation("com.auth0:java-jwt:3.4.0")
  implementation("com.google.android.gms:play-services-location:21.1.0")
  implementation("androidx.work:work-runtime:2.9.0")

  // smart-refresh  reference: https://gitee.com/scwang90/SmartRefreshLayout
  implementation("io.github.scwang90:refresh-layout-kernel:2.1.0")
  implementation("io.github.scwang90:refresh-header-classics:2.0.6")
  implementation("io.github.scwang90:refresh-header-radar:2.0.6")
  implementation("io.github.scwang90:refresh-header-falsify:2.0.6")
  implementation("io.github.scwang90:refresh-header-material:2.0.6")
  implementation("io.github.scwang90:refresh-header-two-level:2.0.6")
  implementation("io.github.scwang90:refresh-footer-ball:2.0.6")
  implementation("io.github.scwang90:refresh-footer-classics:2.0.6")

  // libui-module
  implementation(project(":libui"))

  // baidu map
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(files("libs/BaiduLBS_Android.jar"))

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