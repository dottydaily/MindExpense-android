plugins {
    alias(libs.plugins.convention.featureCompose)
    alias(libs.plugins.convention.koinAndroid)
}

android {
    namespace = "com.purkt.mindexpense.features.%%FEATURE_NAME%%"
}

dependencies {
    implementation(projects.core.android)
}