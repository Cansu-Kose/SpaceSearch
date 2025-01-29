# Screenshots

<p align="center"  width="70%">
   <img width="50%" src="https://github.com/Cansu-Kose/SpaceSearch/blob/main/image/splash_screen.png" alt="Sublime's custom image"/>
  <img width="50%" src="https://github.com/Cansu-Kose/SpaceSearch/blob/main/image/no_result_found_screen.png" alt="Sublime's custom image"/>
  <img width="50%" src="https://github.com/Cansu-Kose/SpaceSearch/blob/main/image/please_type_to_search_screen.png" alt="Sublime's custom image"/>
  <img width="50%" src="https://github.com/Cansu-Kose/SpaceSearch/blob/main/image/search_result_screen.png" alt="Sublime's custom image"/>
</p>

# SpaceSearch

This project is an Android application built with Kotlin and Jetpack Compose. It follows a modular structure that separates concerns into distinct layers such as data, repository, ui, and viewmodel, making the codebase easier to scale and maintain. Dependency Injection is handled by Hilt, while networking relies on Retrofit. Below is a brief overview of the project structure and the third-party libraries used.

## Project Structure

```bash
com.example.spacesearch
├─ application
│  └─ SpSearch (Application class initialization)
│
├─ data
│  ├─ common
│  │  └─ DataState (Common classes/data structures)
│  ├─ datasource.remote
│  │  └─ SearchRemoteDataSource (Handles remote API calls)
│  ├─ model
│  │  ├─ entity (Data classes representing local/domain models)
│  │  └─ response (Data classes representing API responses)
│  ├─ repository
│  │  ├─ SearchRepository (Implements data retrieval logic)
│  │  └─ SearchRepositoryInterFace (Defines repository interfaces)
│  └─ service.remote
│     └─ SearchAPIService (Retrofit service definitions)
│
├─ di
│  ├─ AppModule.kt (Hilt modules for app-level dependencies)
│  └─ RepositoryModule.kt (Hilt modules for repository/data-level dependencies)
│
├─ navigation
│  └─ (Navigation graph definitions using Jetpack Navigation)
│
├─ ui
│  ├─ component
│  │  └─ screens
│  │     └─ SearchBar.kt (Example UI composable)
│  └─ theme (Contains theming and styling for Jetpack Compose)
│
└─ viewmodel
   ├─ MainViewModel (Manages UI-related data for the main screen)
   └─ (Other ViewModels if needed)

MainActivity (Holds activity-level UI content)

```


## Highlights

* application: Contains the main SpSearch application class.
* data: Responsible for data-related logic (remote data sources, models, repositories, etc.).
* di: Houses Hilt modules for configuring Dependency Injection.
* navigation: Defines navigation routes and destinations for Compose-based navigation.
* ui: Holds composable screens, components, and theming.
* viewmodel: Contains ViewModel classes that provide data to the UI.

# Third-Party Libraries

Below is a short description of each major third-party library used, grouped by functionality.

## AndroidX Core & Jetpack

### `androidx.core:core-ktx`
Provides Kotlin extensions for Android framework APIs to make them more concise and idiomatic.

### Jetpack Compose (`androidx.compose.*`)
Modern toolkit for building native Android UIs declaratively.  
Various modules include `ui`, `ui-tooling`, `ui-graphics`, and `material3` for Material Design 3 components.

### `androidx.lifecycle:lifecycle-runtime-ktx`
Adds lifecycle-aware components and Kotlin extensions (e.g., `repeatOnLifecycle`).

### `androidx.activity:activity-compose`
Integrates Compose with traditional Android `Activity` APIs.

### `androidx.constraintlayout:constraintlayout-compose`
Constraint layout support in Jetpack Compose for complex UI designs.

### `androidx.navigation.*`
Navigation components for seamless screen transitions (`navigation-compose` for Compose integration).

### `androidx.core:core-splashscreen`
Splash screen API for a custom startup experience.

### `androidx.paging.compose`
Paging library integration with Jetpack Compose for large data sets or infinite scrolling lists.

### `androidx.multidex:multidex`
Enables MultiDex support for applications with more than 65,536 methods.

### `com.google.android.material:material`
Material Design components for Android (includes classic Material styling).

---

## Dependency Injection

### Hilt (Dagger Hilt)
- `com.google.dagger:hilt-android`, `hilt-compiler`, `hilt-navigation-compose`  
Simplifies DI setup in Android apps. Scopes dependencies to lifecycles and integrates with ViewModels/Compose.

---

## Networking & JSON

### Retrofit
- `com.squareup.retrofit2:retrofit`  
Type-safe HTTP client for making network requests.

### Gson
- `com.google.code.gson:gson` and `com.squareup.retrofit2:converter-gson`  
Converts JSON into Kotlin objects and vice versa.

### OkHttp Logging Interceptor
- `com.squareup.okhttp3:logging-interceptor`  
Logs HTTP request and response data for easier debugging.

---

## Image Loading

### Landscapist
- `com.github.skydoves:landscapist-coil`, `landscapist-placeholder`, `landscapist-animation`  
A set of libraries built on top of [Coil](https://coil-kt.github.io/coil/) for image loading in Compose.  
Handles placeholders, animations, and caching efficiently.

---

## Firebase

### `com.google.firebase:firebase-config`
Retrieve and apply remote configurations at runtime to dynamically alter behavior and appearance.

---

## Testing

### JUnit & AndroidX Test
- `junit`, `androidx.test.ext:junit`, `androidx.test.espresso:espresso-core`  
Standard Java unit testing plus Android instrumentation testing for UI.

### Compose Testing
- `androidx.compose.ui:ui-test-junit4` and related tooling  
Enables UI tests for composables with JUnit integration.

### Hilt Testing
- `com.google.dagger:hilt-android-testing`  
Supports creating test components for DI setup in tests.

### MockK
- `io.mockk:mockk` and related modules  
Kotlin-based mocking library for writing unit tests.

### Turbine
- `app.cash.turbine:turbine`  
A testing library for `Flow`-based coroutines.

---

## Kotlin Coroutines

### `org.jetbrains.kotlinx:kotlinx-coroutines-core`
Concurrency design pattern for asynchronous tasks.

### `org.jetbrains.kotlinx:kotlinx-coroutines-test`
Testing support for coroutines (e.g., `runTest`, `TestCoroutineDispatcher`).

---

## Logging

### Timber
- `com.jakewharton.timber:timber`  
Lightweight, flexible logging library for easier debug logging.

---

## Getting Started

1. **Clone the repository** and open it in **Android Studio** (latest stable version recommended).
2. **Configure your Firebase settings** if needed by adding the appropriate `google-services.json`.
3. **Run the project** on a compatible emulator or physical device.

---

## Contributing

Feel free to open issues or submit pull requests for any bug fixes or enhancements. We welcome contributions from the community to make **SpaceSearch** more robust and feature-rich.


## License
This project is licensed under the [MIT](https://choosealicense.com/licenses/mit/) License. Feel free to use it as a reference or integrate the concepts in your own projects, but please adhere to the license terms

