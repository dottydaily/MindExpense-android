plugins {
    alias(libs.plugins.convention.featureCompose)
    alias(libs.plugins.convention.koinAndroid)
}

android {
    namespace = "com.purkt.mindexpense.home"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain.expense)
    implementation(projects.core.domain.users)
}