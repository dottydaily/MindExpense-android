plugins {
    alias(libs.plugins.convention.main.jvmLibrary)
    alias(libs.plugins.convention.jvm.koin)
    alias(libs.plugins.convention.jvm.testCore)
}

dependencies {
    implementation(libs.mockk) // For creating helper classes for testing purpose
    testImplementation(projects.core.testing)
}