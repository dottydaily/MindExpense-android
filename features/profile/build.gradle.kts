plugins {
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.koinAndroid)
}

android {
    namespace = "com.purkt.mindexpense.features.profile"
}

dependencies {
    implementation(projects.core.android)
    implementation(projects.core.domain.users)
}