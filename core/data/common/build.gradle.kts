plugins {
    alias(libs.plugins.convention.libraryJvm)
    alias(libs.plugins.convention.koinCore)
}

dependencies {
    implementation(projects.core.logging)
    implementation(libs.kotlinx.coroutines)
    testImplementation(libs.junit)
}