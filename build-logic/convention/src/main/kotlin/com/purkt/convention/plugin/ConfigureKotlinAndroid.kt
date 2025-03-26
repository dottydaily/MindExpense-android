package com.purkt.convention.plugin

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = AndroidBuildConfig.COMPILE_SDK

        defaultConfig {
            minSdk = AndroidBuildConfig.MIN_SDK

            // Set the default test instrumentation runner for instrumented tests.
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            // Enable support library for vector drawables
            vectorDrawables.useSupportLibrary = true
        }

        buildFeatures {
            // Enable BuildConfig generation for accessing build-time constants.
            buildConfig = true
        }

        compileOptions {
            // Enable core library desugaring
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        configureKotlin()

        dependencies {
            coreLibraryDesugaring(libs.findLibrary("android-coreLibraryDesugaring").get())
            implementation(libs.findLibrary("timber").get())
        }
    }
}