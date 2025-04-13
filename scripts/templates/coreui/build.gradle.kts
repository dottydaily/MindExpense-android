plugins {
    alias(libs.plugins.convention.main.androidLibrary)
    alias(libs.plugins.convention.library.compose)
}

android {
    namespace = "com.purkt.mindexpense.core.ui.%%FEATURE_NAME%%"
}

dependencies {
    implementation(libs.androidx.coreKtx)
    implementation(libs.bundles.composeBasic)
}