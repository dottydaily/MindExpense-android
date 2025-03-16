plugins {
    alias(libs.plugins.convention.library)
}

android {
    namespace = "com.purkt.mindexpense.core.common"
}

dependencies {
    implementation(libs.androidx.coreKtx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}