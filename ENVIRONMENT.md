# Catatan Environment Proyek (Environment Documentation)

Dokumen ini berisi spesifikasi lingkungan pengembangan (*development environment*), konfigurasi build, serta pustaka (*dependencies*) yang digunakan pada proyek Android ini.

---

##  Informasi Umum Proyek
| Parameter | Nilai |
| :--- | :--- |
| **Nama Proyek** | `0112523013_FachriReyhan_P1` |
| **Application ID / Package** | `com.example.a0112523013_fachrireyhan_p1` |
| **Mata Kuliah** | Mobile Programming |
| **Bahasa Pemrograman** | Kotlin |
| **UI Framework** | Jetpack Compose (Declarative UI) |

---

##  Konfigurasi SDK & Build System
| Komponen | Versi / Konfigurasi | Deskripsi |
| :--- | :--- | :--- |
| **Compile SDK** | `API 37` | Versi SDK Android yang digunakan saat kompilasi |
| **Target SDK** | `API 37` | Versi SDK Android target pengujian aplikasi |
| **Min SDK** | `API 24` | Minimal Android 7.0 (Nougat) untuk menjalankan aplikasi |
| **Java Compatibility** | `Java 11` (`JavaVersion.VERSION_11`) | Kompatibilitas versi Java untuk kode bytecode |
| **Gradle Version** | `9.6.0` | Versi Gradle Build Tool (`gradle-wrapper.properties`) |
| **Android Gradle Plugin (AGP)** | `9.4.1` | Plugin AGP (`libs.versions.toml`) |
| **Kotlin Plugin / Version** | `2.2.10` | Versi bahasa Kotlin & plugin Compose Compiler |

---

##  Pustaka & Dependencies Utama (`gradle/libs.versions.toml`)

###  UI & Jetpack Compose
* **Compose BOM**: `2026.02.01`
* **Material 3**: Managed by BOM (`androidx.compose.material3`)
* **Activity Compose**: `1.8.0` (`androidx.activity:activity-compose`)
* **UI Tooling & Preview**: Managed by BOM (`androidx.compose.ui:ui-tooling-preview`)

### Core & Lifecycle
* **Core KTX**: `1.10.1` (`androidx.core:core-ktx`)
* **Lifecycle Runtime KTX**: `2.6.1` (`androidx.lifecycle:lifecycle-runtime-ktx`)

### Testing Frameworks
* **JUnit**: `4.13.2` (Unit Testing)
* **AndroidX Test JUnit**: `1.1.5` (Instrumentation Testing)
* **Espresso Core**: `3.5.1` (UI Testing)
* **Compose Test JUnit4**: Managed by BOM

---

## Prasyarat Lingkungan Pengembangan (Prerequisites)

1. **Android Studio**: Android Studio terbaru (Direkomendasikan versi 2024.1+ / Ladybug / Jellyfish / Meerkat).
2. **JDK**: JDK 11 atau lebih tinggi (JDK 17/21 terintegrasi pada Android Studio modern).
3. **Android Emulator / Device**:
   - Sistem Operasi minimal Android 7.0 (API Level 24).
   - Diutamakan Android 14/15/16 (API 34–37) untuk performa & fitur terbaik.

---

## Perintah Dasar Gradle (Command Line)

Gunakan *Gradle Wrapper* (`gradlew` / `gradlew.bat`) untuk menjalankan perintah berikut dari terminal proyek:

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
