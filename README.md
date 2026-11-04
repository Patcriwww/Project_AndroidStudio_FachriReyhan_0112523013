# Student Profile Manager - Mobile Programming

[![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=flat&logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin_2.2.10-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-757575.svg?style=flat&logo=materialdesign)](https://m3.material.io)[![Architecture](https://img.shields.io/badge/Architecture-MVVM_--_UDF_--_Clean-blue.svg?style=flat)](https://developer.android.com/topic/architecture)

Repository ini berisi proyek **Student Profile Manager** untuk mata kuliah **Mobile Programming (Semester 7)**. Aplikasi ini dibangun menerapkan **Unidirectional Data Flow (UDF)**, **StateFlow**, **Sealed Interface Events**, **Type-Safe Navigation 2.8+**, **Stateless UI Decoupling**, serta **Unit Test Suite** (Validator & ViewModel).

---

## Identitas Mahasiswa

* **Nama**: Fachri Reyhan
* **NIM**: 0112523013
* **Program Studi**: Informatika - Universitas Al Azhar Indonesia
* **Mata Kuliah**: Mobile Programming (Semester 7)

---

## Fitur Utama & Layar Aplikasi

Aplikasi dibangun dengan arsitektur **UDF (Unidirectional Data Flow)** di mana layar UI bersifat **Stateless** dan terhubung melalui *Route Containers*:

### 1. Layar Daftar Mahasiswa (`StudentListScreen` & `StudentListRouteContent`)
- **State-Driven List**: Menampilkan daftar 8 mahasiswa simulasi (`StudentSeed`).
- **Pencarian Real-time**: Filter pencarian nama/NIM yang memicu `StudentEvent.SearchChanged`.
- **Stateless Screen**: Menerima `StudentListUiState` dan mentransmisikan callback interaksi ke ViewModel.

### 2. Layar Detail Mahasiswa (`StudentDetailScreen` & `StudentDetailRouteContent`)
- **Single Source of Truth**: Menampilkan rincian data mahasiswa berdasarkan ID unik via `StudentDetailUiState`.
- **Navigasi Edit & Kembali**: Menghubungkan pengguna ke form edit tanpa duplikasi state.

### 3. Layar Form Tambah & Edit (`StudentFormScreen` & `StudentFormRouteContent`)
- **Session Key Draft Retention**: Menggunakan ID `NavBackStackEntry` sebagai `sessionKey` agar data form tidak ter-reset saat layar dirotasi.
- **Validasi Terpusat**: Semua validasi (Nama, NIM 10-digit, Regex Email, Jangkauan Semester, & **NIM Unik**) diproses penuh di ViewModel.
- **Sealed Event Handling**: Aksi form diproses via `StudentEvent` dan mengembalikan `StudentEventResult` (`Saved` / `Message` / `None`).

---

## Arsitektur & Unidirectional Data Flow (UDF)

```text
               +----------------------------------+
               |          User Interaction        |
               +----------------------------------+
                                |  (StudentEvent)
                                v
                       +------------------+
                       | StudentViewModel |
                       +------------------+
                                |  (StateFlow<StudentUiState>)
                                v
                     +----------------------+
                     | Route Containers     |
                     | (StudentRoutes.kt)   |
                     +----------------------+
                                |  (UiState & Callbacks)
                                v
                     +----------------------+
                     | Stateless Screens    |
                     | (List, Detail, Form) |
                     +----------------------+
```

1. **Immutable UI State (`StudentUiState.kt`)**: Satu sumber kebenaran (*single source of truth*) untuk seluruh aplikasi (`StudentUiState`, `StudentFormUiState`, `StudentListUiState`, `StudentDetailUiState`).
2. **Sealed Events & Results**: Seluruh aksi UI menggunakan `StudentEvent` dan dikelola terpusat oleh `StudentViewModel.onEvent()`.
3. **Stateless UI Decoupling**: Layar UI (`StudentListScreen`, `StudentDetailScreen`, `StudentFormScreen`) 100% *stateless*, tidak bergantung pada instance `ViewModel`.

---

## Unit Test Suite (`app/src/test/`)

Aplikasi dilengkapi dengan **13 Unit Test** yang lulus 100%:

* **`StudentValidatorTest.kt`** (5 Tests):
  - Validasi batas nama min 3 karakter.
  - Validasi NIM 10-digit angka.
  - Validasi format email Regex.
  - Validasi jangkauan semester (1-14).
  - Validasi penolakan NIM ganda.
* **`StudentViewModelTest.kt`** (8 Tests):
  - Ketersediaan 8 mahasiswa awal.
  - Perubahan `searchQuery` pada immutable state.
  - Retensi draft form saat rotasi (*sessionKey*).
  - Penambahan mahasiswa baru secara sukses.
  - Penolakan NIM tidak valid.
  - Fitur Reset yang mengembalikan nilai asli.
  - Penanganan ID mahasiswa yang tidak terdaftar.
  - Verifikasi imutabilitas snapshot state.

---

## Tech Stack & Spesifikasi

| Komponen | Versi / Spesifikasi |
| :--- | :--- |
| **Bahasa Pemrograman** | Kotlin 2.2.10 |
| **UI Framework** | Jetpack Compose (BOM `2026.02.01`) |
| **Design System** | Material Design 3 (`androidx.compose.material3`) |
| **Arsitektur UI** | UDF + Stateless Composables + Route Containers |
| **Navigasi** | Navigation Compose 2.8+ Type-Safe (`AppRoutes.kt`) |
| **State Management** | StateFlow, `rememberSaveable`, ViewModel activity-scoped |
| **Testing** | JUnit 4 (`StudentValidatorTest` & `StudentViewModelTest`) |
| **Compile / Target SDK** | API Level 37 |
| **Minimum SDK** | API Level 24 (Android 7.0 Nougat) |
| **Gradle / AGP** | Gradle 9.6.0 / AGP 9.4.1 |

---

## Struktur Proyek

```text
0112523013_FachriReyhan_Project1/
├── app/src/main/java/com/example/a0112523013_fachrireyhan_p1/
│   ├── data/
│   │   └── Student.kt              # Data Model & StudentSeed (8 profil)
│   ├── navigation/
│   │   └── AppRoutes.kt            # Type-safe Navigation Routes & NavHost
│   ├── state/
│   │   └── StudentUiState.kt       # Immutable UI States, StudentEvent, & StudentEventResult
│   ├── validation/
│   │   └── StudentValidator.kt     # Pure validation functions
│   ├── viewmodel/
│   │   └── StudentViewModel.kt     # ViewModel UDF Event Handler & StateFlow
│   ├── ui/
│   │   ├── ProfileComponents.kt    # Reusable Stateless UI Components
│   │   ├── StudentListScreen.kt    # Stateless List Screen
│   │   ├── StudentDetailScreen.kt  # Stateless Detail Screen
│   │   ├── StudentFormScreen.kt    # Stateless Form Screen
│   │   └── route/
│   │       └── StudentRoutes.kt    # Route Containers (ViewModel-to-Screen Bridge)
│   ├── ui/theme/                   # Color, Theme, & Typography
│   └── MainActivity.kt             # Entry Point Host App
├── app/src/test/java/com/example/a0112523013_fachrireyhan_p1/
│   ├── StudentValidatorTest.kt     # Unit Test Suite untuk fungsi validasi
│   └── StudentViewModelTest.kt     # Unit Test Suite untuk ViewModel & UDF Event
├── ENVIRONMENT.md                  # Dokumentasi Lingkungan Pengembangan
└── README.md                       # Dokumentasi Utama Repository
```

---

## Cara Menjalankan Proyek & Unit Test

1. **Clone Repository**:
   ```bash
   git clone https://github.com/username/0112523013_FachriReyhan_Project1.git
   cd 0112523013_FachriReyhan_Project1
   ```

2. **Buka Proyek di Android Studio**:
   - Buka **Android Studio** (Ladybug / Jellyfish / Meerkat atau versi 2024.1+).
   - Pilih **Open** dan arahkan ke folder proyek ini.
   - Tunggu hingga proses **Gradle Sync** selesai.

3. **Jalankan Aplikasi**:
   - Hubungkan perangkat fisik via USB Debugging atau pilih Android Emulator (Minimal API 24).
   - Klik tombol **Run 'app'** (`Shift + F10`).

4. **Menjalankan Seluruh Unit Test**:
   - Jalankan perintah berikut di Terminal:
     ```bash
     ./gradlew test
     ```

---

## Catatan Tambahan

Dokumen lengkap mengenai spesifikasi lingkungan pengembangan (*development environment*), pustaka dependencies, dan perintah CLI Gradle dapat dilihat pada file **[ENVIRONMENT.md](ENVIRONMENT.md)**.
