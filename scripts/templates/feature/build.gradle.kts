plugins {
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.koinAndroid)
}

android {
    namespace = "com.purkt.mindexpense.features.%%FEATURE_NAME%%"
}

dependencies {
    implementation(projects.core.android)
}