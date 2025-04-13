plugins {
    alias(libs.plugins.convention.main.androidLibrary)
    alias(libs.plugins.convention.android.xml)
    alias(libs.plugins.convention.library.compose)
}

android {
    namespace = "com.purkt.mindexpense.core.ui.common"
}

dependencies {
    implementation(projects.core.data.common)

    implementation(libs.androidx.coreKtx)
    implementation(libs.bundles.composeBasic)
}