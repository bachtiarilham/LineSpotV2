// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false // 📱 Android Application Plugin - Used for Android app development
    alias(libs.plugins.kotlin.android) apply false // 🟩 Kotlin Android Plugin - Enables Kotlin for Android
    alias(libs.plugins.kotlin.compose) apply false // 🎨 Kotlin Compose Plugin - Supports Jetpack Compose UI development
    alias(libs.plugins.firebase.appdistribution) apply false

    // ⚙️ KSP (Kotlin Symbol Processing) - Used for annotation processing
    id("com.google.devtools.ksp") version libs.versions.ksp.get() apply false
    // 🏗️ Hilt Dependency Injection - Simplifies DI in Android
    id("com.google.dagger.hilt.android") version libs.versions.hiltAndroid.get() apply false
}
