plugins {
    alias(libs.plugins.convention.libraryAndroid)
    alias(libs.plugins.convention.koinAndroid)
    alias(libs.plugins.convention.testCore)
}

android {
    namespace = "com.purkt.mindexpense.core.android"
}

dependencies {
    implementation(projects.core.logging)

    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycleViewModelKtx)

    testImplementation(projects.core.testing)
}