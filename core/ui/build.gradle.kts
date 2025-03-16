plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.composeLibrary)
}

android {
    namespace = "com.purkt.mindexpense.core.ui"
}

dependencies {
    implementation(libs.androidx.coreKtx)
    implementation(libs.bundles.composeBasic)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}