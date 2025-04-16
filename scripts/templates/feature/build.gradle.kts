plugins {
    alias(libs.plugins.convention.main.androidFeature)
    alias(libs.plugins.convention.android.koin)
}

android {
    namespace = "com.purkt.mindexpense.features.%%FEATURE_NAME%%"
}

dependencies {
    implementation(projects.core.android)
}