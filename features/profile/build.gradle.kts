plugins {
    alias(libs.plugins.convention.featureCompose)
    alias(libs.plugins.convention.koinAndroid)
}

android {
    namespace = "com.purkt.mindexpress.profile"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain.users)
}