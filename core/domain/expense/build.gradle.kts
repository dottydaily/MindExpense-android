plugins {
    alias(libs.plugins.convention.libraryAndroid)
    alias(libs.plugins.convention.koinCore)
}

android {
    namespace = "com.purkt.mindexpense.core.domain.expense"
}

dependencies {
    api(projects.core.data.expense)
    implementation(projects.core.logging)

    implementation(libs.androidx.coreKtx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}