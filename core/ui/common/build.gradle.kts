plugins {
    alias(libs.plugins.convention.libraryAndroid)
    alias(libs.plugins.convention.xml)
    alias(libs.plugins.convention.composeLibrary)
}

android {
    namespace = "com.purkt.mindexpense.core.ui.common"
}

dependencies {
    implementation(projects.core.data.common)

    implementation(libs.androidx.coreKtx)
    implementation(libs.bundles.composeBasic)
}