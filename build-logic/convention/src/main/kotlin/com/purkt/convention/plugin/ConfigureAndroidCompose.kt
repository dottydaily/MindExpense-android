package com.purkt.convention.plugin

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        // Enable Compose features.
        buildFeatures {
            compose = true
        }

        // Add Compose dependencies.
        dependencies {
            // Retrieve the Compose BOM (Bill of Materials).
            val bom = libs.findLibrary("platform-composeBom").get()

            // Import the Compose BOM to manage Compose library versions.
            implementation(platform(bom))
            // Import the Compose BOM for Android Test.
            androidTestImplementation(platform(bom))

            // Add Compose tooling preview library.
            implementation(libs.findLibrary("compose-uiToolingPreview").get())
            // Add Compose tooling library (for debugging).
            debugImplementation(libs.findLibrary("compose-uiTooling").get())
            // Add Compose UI test manifest for testing.
            debugImplementation(libs.findLibrary("compose-uiTestManifest").get())
        }
    }
}