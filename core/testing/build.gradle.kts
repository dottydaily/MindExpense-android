plugins {
    alias(libs.plugins.convention.libraryJvm)
    alias(libs.plugins.convention.testCore)
    alias(libs.plugins.convention.koinCore)
}

dependencies {
    implementation(projects.core.logging)
}