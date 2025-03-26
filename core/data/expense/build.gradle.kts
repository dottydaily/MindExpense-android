plugins {
    alias(libs.plugins.convention.libraryJvm)
    alias(libs.plugins.convention.koinCore)
    alias(libs.plugins.convention.roomCommonNonAndroid)
}

dependencies {
    implementation(projects.core.data.common)

    implementation(libs.kotlinx.coroutines)
    testImplementation(libs.junit)
}