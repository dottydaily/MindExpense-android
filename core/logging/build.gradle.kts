plugins {
    alias(libs.plugins.convention.main.jvmLibrary)
    alias(libs.plugins.convention.jvm.koin)
    alias(libs.plugins.convention.jvm.testCore)
}

dependencies {
    testImplementation(projects.core.testing)
}