plugins {
    alias(libs.plugins.convention.main.androidLibrary)
    alias(libs.plugins.convention.library.compose)
}

android {
    namespace = "com.purkt.mindexpense.core.ui.expense"
}

dependencies {
    implementation(libs.androidx.coreKtx)
    implementation(libs.bundles.composeBasic)
    implementation(projects.core.data.common)
    implementation(projects.core.data.expense)
    implementation(projects.core.ui.common)
}