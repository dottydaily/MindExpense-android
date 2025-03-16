# How to setup Convention Plugin in your project

> Ref guide : (Link)[https://medium.com/@fatiharslan2634/mastering-android-multi-module-architecture-with-convention-plugins-bfee89f3ec38]

1. Prepare your Version Catalog file in `/gradle/libs.versions.toml`.
2. Create module for setup Convention Plugin. (In this project is `build-logic`).
3. create `settings.gradle.kts` inside `build-logic`
```kotlin
dependencyResolutionManagement {
    // Dependency repository for the plugins
    repositories {
        google()
        mavenCentral()
    }
    // This is where we add the version catalog
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
```
4. Add `gradle.properties` file to `build-logic` module. 
   - This is only a workaround since it can't use configs in `gradle.properties` from root directory)
```
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configureondemand=true
```
5. Sync gradle one time to setup `build-logic` module.
6. Create module for containing all Convention plugins. (In this project is `convention`)
   - This module must be located under `build-logic`
   - `build-logic` is just a parent module for setting things, then `convention` will be child module which containing all source codes of plugins.
7. Sync gradle one time to register `convention` module, then add `include(":convention")` in `settings.gradle.kts` of `build-logic` module and sync gradle again.
8. Create `build.gradles.kts` in `convention` module and add these following codes.
```kotlin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

// Configure all Kotlin compilation tasks.
// More info : https://developer.android.com/build/gradle-build-overview#is-build?
tasks.withType<KotlinCompile>().configureEach {
    // Configure the Kotlin options for each Kotlin compilation task.
    compilerOptions {
        // Sets the JVM target version for Kotlin compilation to Java 17.
        // This ensures that the compiled Kotlin code is compatible with Java 17.
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

// Define dependencies for the project.
dependencies {
    // Add the Android Gradle plugin as a compile-only dependency.
    compileOnly(libs.android.tools.build.gradle.plugin)
    // Add the Android tools common library as a compile-only dependency.
    compileOnly(libs.android.tools.common)
    // Add the Kotlin Gradle plugin as a compile-only dependency.
    compileOnly(libs.kotlin.gradlePlugin)
    // Add the KSP (Kotlin Symbol Processing) Gradle plugin as a compile-only dependency.
    compileOnly(libs.ksp.gradlePlugin)
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
```
9. Sync gradle one time to setup `convention` module.
    - If you found some error while syncing, please try to comment the error part, sync gradle, then uncomment and sync gradle again. Do these steps repeatly until the error is gone.
10. Then start defines your own Convention Plugins in `/build-logic/convention/src/main/`.
11. Register all of your defined plugin by adding these in the `build.gradle.kts` of `:build-logic:convention` module.
```kotlin
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
        register("androidLibraryConvention") {
            id = "convention.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryComposeConvention") {
            id = "convention.compose-library"
            implementationClass = "LibraryComposeConventionPlugin"
        }
    }
}
```
12. Apply your target plugin to your module by adding these codes in `build.gradle.kts` of that module.
```kotlin

```