plugins {
    alias(libs.plugins.convention.main.androidLibrary)
    alias(libs.plugins.convention.android.koin)
    alias(libs.plugins.convention.jvm.testCore)
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