plugins {
    alias(libs.plugins.convention.main.jvmLibrary)
    alias(libs.plugins.convention.jvm.testCore)
    alias(libs.plugins.convention.jvm.koin)
}

dependencies {
    implementation(libs.mockk)
    implementation(libs.koin.testCore)
    implementation(libs.koin.testJunit4)
}