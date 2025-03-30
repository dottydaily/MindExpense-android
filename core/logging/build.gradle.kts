plugins {
    alias(libs.plugins.convention.libraryJvm)
    alias(libs.plugins.convention.koinCore)
    alias(libs.plugins.convention.testCore)
}

dependencies {
    testImplementation(projects.core.testing)
}