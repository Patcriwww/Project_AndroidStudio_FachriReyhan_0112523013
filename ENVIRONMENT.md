# Catatan Environment Proyek (Environment Documentation)

Dokumen ini berisi spesifikasi lingkungan pengembangan (*development environment*), konfigurasi build, serta pustaka (*dependencies*) yang digunakan pada proyek Android ini.

---

## Informasi Umum Proyek
| Parameter | Nilai |
| :--- | :--- |
| **Nama Proyek** | `0112523013_FachriReyhan_P1` |
| **Application ID / Package** | `com.example.a0112523013_fachrireyhan_p1` |
| **Versi Aplikasi** | `v2.0` (`versionCode = 2`, `versionName = "2.0"`) |
| **Mata Kuliah** | Mobile Programming (Semester 7) |
| **Bahasa Pemrograman** | Kotlin `2.2.10` |
| **UI Framework** | Jetpack Compose (Declarative UI) |
| **Arsitektur Aplikasi** | MVVM + Clean Architecture + StateFlow |

---

## Konfigurasi SDK & Build System
| Komponen | Versi / Konfigurasi | Deskripsi |
| :--- | :--- | :--- |
| **Compile SDK** | `API 37` | Versi SDK Android yang digunakan saat kompilasi |
| **Target SDK** | `API 37` | Versi SDK Android target pengujian aplikasi |
| **Min SDK** | `API 24` | Minimal Android 7.0 (Nougat) untuk menjalankan aplikasi |
| **Java Compatibility** | `Java 11` (`JavaVersion.VERSION_11`) | Kompatibilitas versi Java untuk kode bytecode |
| **Gradle Version** | `9.8.0` / `9.6.0` | Versi Gradle Build Tool (`gradle-wrapper.properties`) |
| **Android Gradle Plugin (AGP)** | `9.4.1` | Plugin AGP (`libs.versions.toml`) |
| **Kotlin Plugin / Version** | `2.2.10` | Versi bahasa Kotlin & plugin Compose Compiler |
| **Kotlin Serialization Plugin** | `org.jetbrains.kotlin.plugin.serialization` | Plugin kompilasi rute bertipe (*Type-Safe Routes*) |

---

## Pustaka & Dependencies Utama (`app/build.gradle.kts`)

### UI & Jetpack Compose
* **Compose BOM**: `2026.02.01`
* **Material 3**: Dikelola oleh BOM (`androidx.compose.material3`)
* **Material Icons Core**: `androidx.compose.material:material-icons-core`
* **Activity Compose**: `1.8.0` (`androidx.activity:activity-compose`)
* **UI Tooling & Preview**: Dikelola oleh BOM (`androidx.compose.ui:ui-tooling-preview`)

### Navigation & Architecture (Type-Safe Navigation)
* **Navigation Compose**: `2.9.8` (`androidx.navigation:navigation-compose:2.9.8`)
* **Lifecycle ViewModel Compose**: `2.9.4` (`androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4`)
* **Kotlinx Serialization JSON**: `1.9.0` (`org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0`)

### Core & Lifecycle
* **Core KTX**: `1.10.1` (`androidx.core:core-ktx`)
* **Lifecycle Runtime KTX**: `2.6.1` (`androidx.lifecycle:lifecycle-runtime-ktx`)

### Testing Frameworks
* **JUnit**: `4.13.2` (Unit Testing - `StudentValidatorTest.kt`)
* **AndroidX Test JUnit**: `1.1.5` (Instrumentation Testing)
* **Espresso Core**: `3.5.1` (UI Testing)
* **Compose Test JUnit4**: Dikelola oleh BOM

---

## Prasyarat Lingkungan Pengembangan (Prerequisites)

1. **Android Studio**: Android Studio terbaru (Direkomendasikan versi 2024.1+ / Ladybug / Jellyfish / Meerkat).
2. **JDK**: JDK 11 atau lebih tinggi (JDK 17/21 terintegrasi pada Android Studio modern).
3. **Android Emulator / Device**:
   - Sistem Operasi minimal Android 7.0 (API Level 24).
   - Diutamakan Android 14/15/16 (API 34–37) untuk performa & fitur terbaik.

---

## Perintah Dasar Gradle (Command Line)

Gunakan *Gradle Wrapper* (`gradlew` / `gradlew.bat`) dari terminal proyek:

* **Mengecek versi Gradle & Environment:**
  ```bash
  ./gradlew -v
  ```
* **Membangun Aplikasi (Debug Build):**
  ```bash
  ./gradlew assembleDebug
  ```
* **Membersihkan Proyek (Clean Build):**
  ```bash
  ./gradlew clean
  ```
* **Menjalankan Unit Test:**
  ```bash
  ./gradlew test
  ```
