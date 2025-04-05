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
  echo ">>> Example >>> sh ./scripts/create_coreui_module.sh <feature_name>"
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

# Check if the core/ui directory exists
if [ -d "core/ui" ]; then
  echo "Found root core/ui module."
else
  if ! mkdir "core/ui"
  then
    echo "Error: Could not create root core/ui directory."
    exit 1
  fi
fi

if ! cd "core/ui"
then
  echo "Error: Could not find core/ui directory."
  exit 1
fi

# Check if the inner Core UI's feature module already exists
if [ -d "$FEATURE_NAME" ]; then
  echo "Error: Core UI's feature module : $FEATURE_NAME already exists."
  exit 1
else
  if ! mkdir "$FEATURE_NAME"
  then
    echo "Error: Could not create Core UI's feature module : $FEATURE_NAME."
    exit 1
  fi
fi

# Go to the created Core UI's feature dir
if ! cd "$FEATURE_NAME"
then
  echo "Error: Could not change to Core UI's feature module : $FEATURE_NAME."
  exit 1
fi

# Copy templates to the core ui module
if ! cp -a "$ROOT_PATH/scripts/templates/coreui/." .
then
  echo "Error: Could not copy templates to Core UI's feature module : $FEATURE_NAME."
  exit 1
fi

# Replace module namespace in build.gradle.kts
if ! sed -i "s/%%FEATURE_NAME%%/$FEATURE_NAME/g" build.gradle.kts
then
  echo "Error: Could not replace FEATURE_NAME in build.gradle.kts"
  exit 1
fi

SRC_MAIN_KOTLIN_PATH="src/main/kotlin/com/purkt/mindexpense/core/ui"

# Rename package module in src/main/kotlin/core/ui/template to src/main/kotlin/core/ui/<feature_name>
if ! mv "$SRC_MAIN_KOTLIN_PATH/template" "$SRC_MAIN_KOTLIN_PATH/$FEATURE_NAME"
then
  echo "Error: Could not rename package module in src/main/kotlin/features/template to src/main/kotlin/features/$FEATURE_NAME"
  exit 1
fi

# Add module path in settings.gradle.kts
if ! cd "$ROOT_PATH"
then
  echo "Error: Could not change to root path."
  exit 1
else
  if ! echo -e "include(\":core:ui:$FEATURE_NAME\")" >> settings.gradle.kts
  then
    echo "Error: Could not add module path in settings.gradle.kts"
    exit 1
  fi
fi

echo "Added Core UI's feature module : $FEATURE_NAME."