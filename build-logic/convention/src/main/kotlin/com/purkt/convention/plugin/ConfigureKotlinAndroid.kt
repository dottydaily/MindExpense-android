package com.purkt.convention.plugin

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

        dependencies {
            coreLibraryDesugaring(libs.findLibrary("android-coreLibraryDesugaring").get())
        }

        configureKotlin()
    }
}

// Configures Kotlin compiler options.
private fun Project.configureKotlin() {
    // Find all tasks of type KotlinCompile and configure each one.
    tasks.withType<KotlinCompile>().configureEach {
        // Configure compiler options for each KotlinCompile task.
        compilerOptions {
            // Set the target JVM version to 17.
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}