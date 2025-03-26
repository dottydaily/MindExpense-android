package com.purkt.convention.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Configures Kotlin compiler options.
fun Project.configureKotlin() {
    // Find all tasks of type KotlinCompile and configure each one.
    tasks.withType<KotlinCompile>().configureEach {
        // Configure compiler options for each KotlinCompile task.
        compilerOptions {
            // Set the target JVM version to 17.
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}