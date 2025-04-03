# MindExpense Android Application

> This repository is in development.

The personal application project for managing daily expenses.

## Goals
- [x] Initial application.
- [x] Apply `convention-plugin` concept.
- [x] Apply `Modularization` concept.
- [x] Design module relationship.
- [x] Setup CI/CD by using [**Github Actions**](https://github.com/features/actions)
- [ ] Implement feature for offline expenses.
- [ ] Implement feature for online expenses.
  - This will allow user to login, sync their expenses on the other devices.
- [ ] Publish to Google Play Store

# Modules relationship.

Here is the conceptual relationship between each modules in this project.

## `:app` module
This will be the main module for an Android components such as Application class, MainActivity.

## `:build-logic` module
This is the subproject for building Convention Plugins. You can read more detail in these references.
- https://docs.gradle.org/current/samples/sample_convention_plugins.html
- https://medium.com/@sridhar-sp/simplify-your-android-builds-a-guide-to-convention-plugins-b9fea8c5e117
- https://github.com/android/nowinandroid for example project.

## `:core` module
This will be where all of shared codes will be located. We will separate them into a smaller module by their purpose.
- ### `:core:android`
  - This will contain an common android-related codes for any feature.

- ### `:core:database`
  - Module for database-related classes, schemas, etc.
  - This project is using [**Room**](https://developer.android.com/training/data-storage/room) database library from **Google**.

- ### `:core:logging`
  - Module for managing logging classes.
  - This module has been designed to be **Kotlin/Java** module in the first place.
    - We do this for make it easy to migrate the project into [**Kotlin Multiplatform**](https://www.jetbrains.com/kotlin-multiplatform/).
  - Any module which is using this should logging via `AppLogger` Object class.
    - `AppLogger` relies on `MyLogger` interface injected by [**Koin**](https://insert-koin.io/).
    - You should inject the implementation class of `MyLogger` in **Koin module**.
    - This project implements `MyLogger` with [**Timber**](https://github.com/JakeWharton/timber) and inject it in `:app` module.

- ### `:core:testing`
  - Module for testing-related classes.
  - We are mainly using [**MockK**](https://mockk.io/) library.

- ### `Modules of Core UI`
  - `:core:ui:common`: This will contain an shared UI related codes for any feature.
  - `:core:ui:resources`: This will contain shared resources file such as text, drawable, etc. for any feature.
  - `:core:ui:<feature_name>`: This will contain an UI related codes of specific features.

- ### `Modules of Core Domain`
  - `:core:domain:<feature_name>`: This will contain an business logicc related codes of specific features.
    - We **won't including** classes which can be defined as the `domain model` in Clean Architecture or the other architecture in this `domain` module.

- ### `Modules of Core Data`
  - Most of these modules are **Kotlin/Java module**. You should avoild making them contain any **Android** related components as much as possible.
  - `:core:data:common`: This will contain shared codes of Helper function, Extension function, etc. which are related to **Non-Android** classes.
  - `:core:data:<feature_name>`: This will contain an data source related codes of specific features. (Including local/remote data source).
    - We **will including** classes which can be defined as the `domain model` in Clean Architecture or the other architecture in this `data` module.
    - For any data class related to **Room**, please try to avoid using **Room**'s Android-related components and dependencies as much as possible to keep it as **Kotlin/Java module**.

## `:features:<feature_name>` module
- This will be a module separated by their responsibility for each feature.
- This module will use `:core:ui<feature_name>` module as a dependency, then if will automatically know their transitive dependency from `domain` and `data` via the corresponding **`:core:ui<feature_name>`** module as well.
- Relation example: `features:expenses` --> **`core:ui:expenses`** --> **`core:domain:expenses`** --> **`core:data:expenses`**.

# About Github Actions.

This project is using [**Github Actions**](https://github.com/features/actions) for managing CI/CD of this application.
You can see all defined actions in `.github/workflows/*.yml`

If you want to use these actions, you can follow the instruction on your Github repository (in case that you forked this repository).
Some variable and encrypted content are stored in `secrets/environments` of the corresponding repository.

## Current workflows
- Deploy `devDebug` variant to Firebase App Distribution.
  - File name: `action-deployDevDebugToAppDist.yml`