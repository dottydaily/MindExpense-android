#!/bin/bash

# Function to check if the current directory is the root of an Android project
is_android_root() {
  # Check if build.gradle or settings.gradle exists in the current directory
  if [[ -f "build.gradle.kts" && -f "settings.gradle.kts" && -d "app" ]]; then
    return 0  # True: found root
  else
    return 1  # False: not root
  fi
}

# Change the current directory until it reach the root of an android project.
find_to_android_root() {
  local current_dir=$(pwd)

  # Go up until we reach the root or the filesystem root
  while [[ "$current_dir" != "/" ]]; do
    if is_android_root; then
      echo "$current_dir"
      exit 0
    fi

    # Move up one directory
    cd ..
    current_dir=$(pwd)
  done

  echo "Error: Could not find Android project root."
  exit 1
}

# call the function
ROOT_PATH="$(find_to_android_root)"

if [ "$1" == "" ]; then
  echo "Error: No feature name provided."
  echo ">>> Example >>> sh ./scripts/create_feature_module.sh <feature_name>"
  exit 1
fi

FEATURE_NAME="$1"
CAPITALIZED="${FEATURE_NAME^}"

# Check if the root path is valid
if [ "$ROOT_PATH" == "" ]; then
  exit 1
else
  if ! cd "$ROOT_PATH"
  then
    echo "Error: Could not change to root path."
    exit
  fi
fi

# Print root path
echo "Root path: $(pwd)"

# Check if the features directory exists
if [ -d "features" ]; then
  echo "Found root features module."
else
  if ! mkdir "features"
  then
    echo "Error: Could not create root features directory."
    exit 1
  fi
fi

if ! cd "features"
then
  echo "Error: Could not find features directory."
  exit 1
fi

# Check if the inner feature module already exists
if [ -d "$FEATURE_NAME" ]; then
  echo "Error: Feature module : $FEATURE_NAME already exists."
  exit 1
else
  if ! mkdir "$FEATURE_NAME"
  then
    echo "Error: Could not create feature module : $FEATURE_NAME."
    exit 1
  fi
fi

# Go to the created feature dir
if ! cd "$FEATURE_NAME"
then
  echo "Error: Could not change to feature module : $FEATURE_NAME."
  exit 1
fi

# Copy templates to the feature module
if ! cp -a "$ROOT_PATH/scripts/templates/feature/." .
then
  echo "Error: Could not copy templates to feature module : $FEATURE_NAME."
  exit 1
fi

# Replace module namespace in build.gradle.kts
if ! sed -i "s/%%FEATURE_NAME%%/$FEATURE_NAME/g" build.gradle.kts
then
  echo "Error: Could not replace FEATURE_NAME in build.gradle.kts"
  exit 1
fi

SRC_MAIN_KOTLIN_PATH="src/main/kotlin/com/purkt/mindexpense/features"

# Rename package module in src/main/kotlin/features/template to src/main/kotlin/features/<feature_name>
if ! mv "$SRC_MAIN_KOTLIN_PATH/template" "$SRC_MAIN_KOTLIN_PATH/$FEATURE_NAME"
then
  echo "Error: Could not rename package module in src/main/kotlin/features/template to src/main/kotlin/features/$FEATURE_NAME"
  exit 1
fi

# Rename koin module file name.
if [ ! -f "$SRC_MAIN_KOTLIN_PATH/$FEATURE_NAME/di/FEATURE_MODULE_FILE.kt" ]; then
  echo "Error: Could not file the template Koin module file".
  exit 1
else
  if ! mv "$SRC_MAIN_KOTLIN_PATH/$FEATURE_NAME/di/FEATURE_MODULE_FILE.kt" "$SRC_MAIN_KOTLIN_PATH/$FEATURE_NAME/di/Feature${CAPITALIZED}Module.kt"
  then
    echo "Error: Could not rename Koin module file".
    exit 1
  fi
fi

# Replace package name in Koin module file.
if ! sed -i "s/%%FEATURE_NAME%%/$FEATURE_NAME/g" "$SRC_MAIN_KOTLIN_PATH/$FEATURE_NAME/di/Feature${CAPITALIZED}Module.kt"
then
  echo "Error: Could not replace package name in Koin module file".
  exit 1
fi

# Replace variable name in Koin module file.
if ! sed -i "s/%%FEATURE_NAME_CAPITALIZED%%/$CAPITALIZED/g" "$SRC_MAIN_KOTLIN_PATH/$FEATURE_NAME/di/Feature${CAPITALIZED}Module.kt"
then
  echo "Error: Could not replace variable name in Koin module file".
  exit 1
fi

# Add module path in settings.gradle.kts
if ! cd "$ROOT_PATH"
then
  echo "Error: Could not change to root path."
  exit 1
else
  if ! echo -e "include(\":features:$FEATURE_NAME\")" >> settings.gradle.kts
  then
    echo "Error: Could not add module path in settings.gradle.kts"
    exit 1
  fi
fi

echo "Added feature module : $FEATURE_NAME."