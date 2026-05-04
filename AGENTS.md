# BaseProject - Agent Notes

## Project Shape
- Single Android app module (`:app`). `decryptlib/` at repo root is **not** a Gradle module (not in `settings.gradle`); it only contains old CMake build artifacts.
- Application ID / namespace: `com.example.baseproject`.
- Native code: CMake build in `app/src/main/cpp/CMakeLists.txt` (library `decrypt-lib`).

## Build & Verification
Use the Gradle wrapper. No CI or pre-commit hooks are configured.

```bash
./gradlew assembleDebug      # debug APK
./gradlew test               # unit tests (currently only placeholders)
./gradlew connectedAndroidTest # instrumented tests (needs device/emulator)
./gradlew lint               # lint check
./gradlew clean              # delete build dirs
```

## Architecture Conventions
- **MVVM** with Dagger Hilt.
  - `MyApplication` is `@HiltAndroidApp`.
  - Activities use `@AndroidEntryPoint`.
  - ViewModels extend `BaseViewmodel` and use `@HiltViewModel`.
- **Base classes** (extend these):
  - `BaseFragment<VB : ViewBinding, VM : BaseViewmodel>` — implement `provideViewBinding()`, `provideViewModel()`, `setUpView()`. `setUpEvent()` wires a shared loading dialog by default.
  - `BaseViewmodel` — exposes `isLoading: StateFlow<Boolean>` via `showLoading()` / `hideLoading()`.
- **UI binding**: Data Binding is enabled (`buildFeatures.dataBinding true`), but fragments use **ViewBinding** in the base class.
- **Navigation**: Jetpack Navigation with SafeArgs. Only graph: `res/navigation/auth_nav.xml` (start destination = `splashFragment`).
- **Repository pattern**: `ShopAppResponsitory` interface + `ShopAppResponsitoryImpl`. Injected into ViewModels.

## Dependency Injection Wiring
- Hilt modules live in `di/`.
  - `NetworkModule` — Retrofit/OkHttp/Gson. Base URL comes from `BuildConfig.BASE_URL`.
  - `RepositoryModule` — binds repository interface to impl.
  - `LocalModule` — Room database (if used).
- Hilt compiler is processed with **KSP** (`ksp "com.google.dagger:hilt-compiler:2.48"`).

## Build Quirks & Warnings
- **Hardcoded local backend**: `BASE_URL` is set to `http://172.20.10.2:8080/` in both `app/build.gradle` (`buildConfigField`) and `Define.kt`. The app consumes `BuildConfig.BASE_URL` at runtime.
- **Annotation processor mismatch**: Glide and Room dependencies use `annotationProcessor`, but the project configures `kapt` and `ksp`. Gradle warns that these should be changed to `kapt` (or `ksp` where supported). This does not currently break the build but creates noise.
- **AGP / compileSdk skew**: AGP 8.5.0 warns that it was only tested up to compileSdk 34, while this project targets 35.
- **CMake warning**: `CMakeLists.txt` is missing a `project()` command.
- **AndroidManifest**: still contains `package="com.example.baseproject"`, which is ignored because `namespace` is declared in `build.gradle`. Safe to remove.

## Testing
- No real test coverage yet. `ExampleUnitTest.kt` and `ExampleInstrumentedTest.kt` are placeholders.

## Key Files
- `app/build.gradle` — app config, dependencies, BASE_URL.
- `app/src/main/cpp/CMakeLists.txt` — native lib build.
- `app/src/main/res/navigation/auth_nav.xml` — navigation graph.
- `di/NetworkModule.kt`, `di/RepositoryModule.kt`, `di/LocalModule.kt` — DI wiring.
