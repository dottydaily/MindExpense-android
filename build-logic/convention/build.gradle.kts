import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// This enables the use of Kotlin for writing Gradle build scripts.
plugins {
    `kotlin-dsl`
}

// Defines the group ID for the this module,
group = "com.purkt.convention.plugin"

// Configure Java settings for the project.
java {
    // Specifies that the source code should be compatible with Java 17.
    sourceCompatibility = JavaVersion.VERSION_17
    // Specifies that the compiled bytecode should be compatible with Java 17.
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    // Configure the Kotlin compiler options.
    compilerOptions {
        // Set the JVM target version for Kotlin compilation to Java 17.
        // This ensures that the compiled Kotlin code is compatible with Java 17.
        jvmTarget = JvmTarget.JVM_17
    }
}

// Define dependencies for the project.
dependencies {
    // Add the Android Gradle plugin as a compile-only dependency.
    compileOnly(libs.android.tools.androidGradlePlugin)
    // Add the Android tools common library as a compile-only dependency.
    compileOnly(libs.android.tools.common)
    // Add the Kotlin Gradle plugin as a compile-only dependency.
    compileOnly(libs.kotlin.gradlePlugin)
    // Add the KSP (Kotlin Symbol Processing) Gradle plugin as a compile-only dependency.
    compileOnly(libs.ksp.gradlePlugin)
    // Add the Room Gradle plugin as a compile-only dependency.
    compileOnly(libs.androidx.roomGradlePlugin)
}

// Configure tasks related to plugin validation.
tasks {
    // Configure the 'validatePlugins' task.
    validatePlugins {
        // Enables stricter validation for plugins, increasing the likelihood of catching errors.
        enableStricterValidation = true
        // Causes the build to fail if any warnings are generated during plugin validation.
        failOnWarning = true
    }
}

// Register all plugins we defined in the :build-logic:convention module.
gradlePlugin {
    plugins {
        register("androidApplicationConvention") {
            id = "convention.android-application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("applicationComposeConvention") {
            id = "convention.compose-application"
            implementationClass = "ApplicationComposeConventionPlugin"
        }
        register("applicationFlavorConvention") {
            id = "convention.flavor-application"
            implementationClass = "ApplicationFlavorConventionPlugin"
        }
        register("androidLibraryConvention") {
            id = "convention.library-android"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("jvmLibraryConvention") {
            id = "convention.library-jvm"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("libraryComposeConvention") {
            id = "convention.compose-library"
            implementationClass = "LibraryComposeConventionPlugin"
        }
        register("libraryFlavorConvention") {
            id = "convention.flavor-library"
            implementationClass = "LibraryFlavorConventionPlugin"
        }
        register("featureComposeConvention") {
            id = "convention.feature-compose"
            implementationClass = "FeatureComposeConventionPlugin"
        }
        register("koinCoreConvention") {
            id = "convention.koin-core"
            implementationClass = "KoinCoreConventionPlugin"
        }
        register("koinAndroidConvention") {
            id = "convention.koin-android"
            implementationClass = "KoinAndroidConventionPlugin"
        }
        register("roomAndroidConvention") {
            id = "convention.room-android"
            implementationClass = "RoomAndroidConventionPlugin"
        }
        register("roomConvention") {
            id = "convention.room-common"
            implementationClass = "RoomJvmConventionPlugin"
        }
        register("testConvention") {
            id = "convention.test-core"
            implementationClass = "JvmTestConventionPlugin"
        }
        register("firebaseApplicationConvention") {
            id = "convention.firebase"
            implementationClass = "FirebaseApplicationConventionPlugin"
        }
    }
}