# BaseProject - Android Base Project

## Project Overview
Android Kotlin base project implementing MVVM architecture for building scalable Android applications.

## Tech Stack
- **Language**: Kotlin
- **Min SDK**: 21 | **Target SDK**: 35
- **Architecture**: MVVM (Model-View-ViewModel)
- **DI**: Dagger Hilt
- **Database**: Room
- **Networking**: Retrofit + OkHttp + Gson
- **Navigation**: Jetpack Navigation Component with SafeArgs
- **UI**: Data Binding, Material Design, ViewPager2
- **Image Loading**: Glide
- **Camera**: CameraX
- **Async**: Kotlin Coroutines

## Project Structure
```
app/src/main/java/com/example/baseproject/
├── base/          # Base classes (BaseViewModel, BaseFragment, etc.)
├── binding/       # Data binding adapters
├── custom_ui/     # Custom UI components
├── data/          # Data layer
│   └── remote/    # API services, network models
├── di/            # Hilt dependency injection modules
├── extensions/    # Kotlin extension functions
├── main/          # Main activity/application
├── ui/            # UI layer (screens)
│   ├── auth/      # Authentication (splash, login)
│   ├── camera/    # Camera functionality
│   └── dialog/    # Dialog fragments
└── utils/         # Utility classes
```

## Build Commands
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Clean build
./gradlew clean

# Check for lint issues
./gradlew lint
```

## Code Conventions
- Follow MVVM pattern: UI observes ViewModel, ViewModel calls Repository
- Use Hilt `@Inject` for dependency injection
- Extend `BaseViewModel` for all ViewModels
- Use Navigation SafeArgs for fragment arguments
- Use Data Binding for UI binding
- Handle network states with sealed classes or Result wrapper
- Use Kotlin Coroutines for async operations

## Key Files
- `app/build.gradle` - App dependencies and build config
- `app/src/main/AndroidManifest.xml` - App manifest
- `app/src/main/res/navigation/` - Navigation graphs
- `di/` - Hilt modules for dependency injection
