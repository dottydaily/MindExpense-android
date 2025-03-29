plugins {
    alias(libs.plugins.convention.libraryAndroid)
    alias(libs.plugins.convention.koinCore)
}

android {
    namespace = "com.purkt.core.mindexpense.domain.users"
}

dependencies {
    api(projects.core.data.users)
    implementation(projects.core.logging)

    implementation(libs.androidx.coreKtx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}