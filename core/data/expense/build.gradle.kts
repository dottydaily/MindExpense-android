plugins {
    alias(libs.plugins.convention.library)
    alias(libs.plugins.convention.koinCore)
}

android {
    namespace = "com.purkt.core.mindexpense.data.expense"
}

dependencies {
    implementation(libs.androidx.coreKtx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}