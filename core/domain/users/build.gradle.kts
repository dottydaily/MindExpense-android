plugins {
    alias(libs.plugins.convention.main.androidLibrary)
    alias(libs.plugins.convention.jvm.koin)
}

android {
    namespace = "com.purkt.mindexpense.core.domain.users"
}

dependencies {
    api(projects.core.data.users)
    implementation(projects.core.logging)

    implementation(libs.androidx.coreKtx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espressoCore)
}