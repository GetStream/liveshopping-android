plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
  alias(libs.plugins.spotless)
  alias(libs.plugins.google.secrets)
}

android {
  namespace = "io.getstream.live.shopping"
  compileSdk = 35

  defaultConfig {
    applicationId = "io.getstream.live.shopping"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

secrets {
  propertiesFileName = "secrets.properties"
  defaultPropertiesFileName = "secrets.defaults.properties"
  ignoreList.add("keyToIgnore") // Ignore the key "keyToIgnore"
  ignoreList.add("sdk.*") // Ignore all keys matching the regexp "sdk.*"
}

dependencies {
  // androidx
  implementation(libs.androidx.startup)

  // Compose
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.lifecycle.runtimeCompose)
  implementation(libs.androidx.lifecycle.viewModelCompose)
  implementation(libs.androidx.compose.material)
  implementation(libs.androidx.compose.material.iconsExtended)
  implementation(libs.landscapist.glide)
  implementation(libs.landscapist.animation)
  implementation(libs.landscapist.placeholder)
  implementation(libs.accompanist.permissions)

  // Stream SDKs
  implementation(libs.stream.chat.compose)
  implementation(libs.stream.chat.offline)
  implementation(libs.stream.video.compose)
  compileOnly(libs.stream.video.previewdata)

  // Coroutines
  implementation(libs.kotlinx.coroutines.android)

  // DI
  implementation(libs.hilt.android)
  implementation(libs.androidx.hilt.navigation.compose)
  ksp(libs.hilt.compiler)

  // Serialization
  implementation(libs.kotlinx.serialization.json)

  // logger
  implementation(libs.stream.log)
}