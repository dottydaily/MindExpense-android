plugins {
    alias(libs.plugins.convention.libraryAndroid)
    alias(libs.plugins.convention.koinAndroid)
    alias(libs.plugins.convention.roomAndroid)
    alias(libs.plugins.convention.flavorLibrary)
}

android {
    namespace = "com.purkt.mindexpense.core.database"
}

dependencies {
    implementation(projects.core.data.expense)
    implementation(projects.core.data.users)

    implementation(libs.androidx.coreKtx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}