// Following guide from https://medium.com/@fatiharslan2634/mastering-android-multi-module-architecture-with-convention-plugins-bfee89f3ec38
pluginManagement {
    // Make build-logic a dependency of the root project
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MindExpense"
// For Type-Safe module access such as project.myLibrary instead of ":myLibrary"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:android")
include(":core:data:common")
include(":core:data:expense")
include(":core:data:users")
include(":core:domain:expense")
include(":core:domain:users")
include(":core:ui:common")
include(":core:ui:expense")
include(":core:database")
include(":core:logging")
include(":core:testing")
include(":features:home")
include(":features:profile")
include(":features:expense")
