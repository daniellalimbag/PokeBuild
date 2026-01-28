# PokeBuild

![PokeBuild](./pokebuild.png)

## Overview
PokeBuild is an Android app project that allows users to create, manage, and save custom Pokemon teams.

## Features
- **AndroidX + Material** for up-to-date UI components
- **Modern SDKs**: minSdk 24, targetSdk 34
- **Gradle Kotlin DSL** configuration

## Tech Stack
- **Platform**: Android
- **Build**: Gradle (Kotlin DSL)
- **Libraries**: AndroidX AppCompat, Activity, ConstraintLayout, Material

## Getting Started
- **Requirements**:
  - Android Studio (Giraffe+ recommended)
  - Android SDK 34
  - JDK 8+ (project compile options set to Java 1.8)

- **Clone**:
  ```bash
  git clone https://github.com/<your-username>/PokeBuild.git
  ```

- **Open in Android Studio**:
  1. File > Open > select the `PokeBuild` directory.
  2. Let Gradle sync complete.

## Build & Run
- Select an Android device or emulator.
- Click Run or execute from terminal:
  ```bash
  ./gradlew :app:assembleDebug
  ```
  The APK will be in `app/build/outputs/apk/debug/`.

## Screenshot
![App Screenshot](./pokebuild.png)

## Project Structure
- `app/` — Android app module
- `build.gradle.kts` — Root Gradle config
- `settings.gradle.kts` — Gradle settings
- `gradle.properties` — Global Gradle properties