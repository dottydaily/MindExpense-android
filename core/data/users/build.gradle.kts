plugins {
    alias(libs.plugins.convention.main.jvmLibrary)
    alias(libs.plugins.convention.jvm.composeCompiler)
    alias(libs.plugins.convention.jvm.koin)
    alias(libs.plugins.convention.jvm.room)
}

dependencies {
    implementation(projects.core.data.common)

    implementation(libs.kotlinx.coroutines)
    testImplementation(libs.junit)
}