# WakeLock — Earn Your Wake-Up

WakeLock is a Kotlin Android alarm app that forces a challenge before the alarm can be dismissed. The app includes a home dashboard, per-alarm configuration, a challenge engine, and a full-screen alarm screen.

## Features

- Create and edit recurring or one-time alarms
- Configure label, time, reminder days, snooze, and challenge settings
- Challenge engine with math-based validation and active alarm flow
- Room-backed local persistence
- Bootstrap setup for next-occurrence scheduling and Android alarm behavior
- Basic statistics and settings screens

## Requirements

- Android Studio or command-line Android SDK
- JDK 17
- Gradle 8.9 or newer

## Local environment setup

1. Install JDK 17.
2. Install Android SDK command-line tools and set `ANDROID_HOME` to `C:\Android\Sdk`.
3. Install the platform packages:
   - `platform-tools`
   - `platforms;android-34`
   - `build-tools;34.0.0`
4. Accept the SDK licenses.
5. Build the app:

```bash
cd C:/Users/aksha/Downloads/Projects/AlarmRock
./gradlew assembleDebug
```

## Run tests

```bash
./gradlew test
```

## Debug APK location

The generated debug APK will be in:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Notes

This project provides a working Android foundation and challenge flow for the requested alarm experience. Some advanced device-specific alarm features depend on device permissions, OEM restrictions, and the Android OS behavior of the target device.
