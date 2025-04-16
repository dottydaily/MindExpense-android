plugins {
    alias(libs.plugins.convention.main.androidFeature)
    alias(libs.plugins.convention.android.koin)
}

android {
    namespace = "com.purkt.mindexpense.features.profile"
}

dependencies {
    implementation(projects.core.android)
    implementation(projects.core.domain.users)
}