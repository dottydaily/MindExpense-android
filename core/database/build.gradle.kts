plugins {
    alias(libs.plugins.convention.main.androidLibrary)
    alias(libs.plugins.convention.android.koin)
    alias(libs.plugins.convention.android.room)
    alias(libs.plugins.convention.library.flavor)
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