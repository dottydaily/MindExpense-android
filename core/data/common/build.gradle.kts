plugins {
    alias(libs.plugins.convention.main.jvmLibrary)
    alias(libs.plugins.convention.jvm.testCore)
    alias(libs.plugins.convention.jvm.koin)
}

dependencies {
    implementation(projects.core.logging)
    testImplementation(projects.core.testing)
}