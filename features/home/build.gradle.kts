plugins {
    alias(libs.plugins.convention.featureCompose)
    alias(libs.plugins.convention.koinAndroid)
}

android {
    namespace = "com.purkt.mindexpense.features.home"
}

dependencies {
    implementation(projects.core.android)
    implementation(projects.core.data.common)
    implementation(projects.core.domain.expense)
    implementation(projects.core.domain.users)
    implementation(projects.core.ui.common)
    implementation(projects.core.ui.resources)
}