plugins {
    alias(libs.plugins.convention.feature)
    alias(libs.plugins.convention.koinAndroid)
}

android {
    namespace = "com.purkt.mindexpense.features.expense"
}

dependencies {
    implementation(projects.core.android)
    implementation(projects.core.data.common)
    implementation(projects.core.domain.expense)
    implementation(projects.core.domain.users)
    implementation(projects.core.ui.common)
    implementation(projects.core.ui.expense)
    implementation(projects.core.logging)
}