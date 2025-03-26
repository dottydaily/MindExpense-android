plugins {
    alias(libs.plugins.convention.libraryAndroid)
}

android {
    namespace = "com.purkt.mindexpense.core.common"
}

dependencies {
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycleViewModelKtx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}