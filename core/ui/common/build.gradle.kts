plugins {
    alias(libs.plugins.convention.libraryAndroid)
    alias(libs.plugins.convention.composeLibrary)
}

android {
    namespace = "com.purkt.core.mindexpense.ui.common"
}

dependencies {
    implementation(libs.androidx.coreKtx)
    implementation(libs.bundles.composeBasic)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}