# MindExpense Android Application

> This repository is in development.

The personal application project for managing daily expenses.

## Goals
- [x] Initial application.
- [x] Apply `convention-plugin` concept.
- [x] Apply `Modularization` concept.
- [x] Design module relationship.
- [ ] Implement feature for offline expenses.
- [ ] Implement feature for online expenses.
  - This will allow user to login, sync their expenses on the other devices.
- [ ] Publish to Google Play Store

# Modules relationship.

Here is the conceptual relationship between each modules in this project.
- `:app`: This will be the main module for an Android components such as Application class, MainActivity.
- `:core`: This will be where all of shared codes will be located. We will separate them into a smaller module by their purpose.
  - `:core:common`: This will contain an common codes of each feature.
  - `:core:ui:<feature_name>`: This will contain an UI related codes of each feature.
  - `:core:domain:<feature_name>: This will contain an business logicc related codes of each feature`.
    - We **__won't including__** classes which can be defined as the `domain model` in Clean Architecture or the other architecture in this `domain` module.
  - `:core:data:<feature_name>`: This will contain an data source related codes of each feature. (Including local/remote data source).
    - We **__will including__** classes which can be defined as the `domain model` in Clean Architecture or the other architecture in this `data` module.
- `:features:<feature_name>`: This will be a module separated by their responsibility for each feature.
  - This module will only use `:core:ui<feature_name>` module as a dependency, then if will automatically know their transitive dependency from `domain` and `data` via the `ui` module as well.
  - Relation example: `features:expenses` --> `core:ui:expenses` --> `core:domain:expenses` --> `core:data:expenses`.