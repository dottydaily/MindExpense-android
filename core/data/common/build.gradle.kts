plugins {
    alias(libs.plugins.convention.libraryJvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines)
    testImplementation(libs.junit)
}