# 🎓 Student Profile Manager - Mobile Programming

[![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=flat&logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin_2.2.10-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-757575.svg?style=flat&logo=materialdesign)](https://m3.material.io)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM_--_Clean-blue.svg?style=flat)](https://developer.android.com/topic/architecture)

Repository ini berisi proyek **Student Profile Manager** untuk mata kuliah **Mobile Programming (Semester 7)**. Aplikasi ini dibangun dengan arsitektur **MVVM (Model-View-ViewModel)**, **Jetpack Compose**, **Type-Safe Navigation 2.8+**, **StateFlow**, **Validasi Data Unik**, serta dilengkapi **Unit Test**.

---

## Identitas Mahasiswa

* **Nama**: Fachri Reyhan
* **NIM**: 0112523013
* **Program Studi**: Informatika - Universitas Al Azhar Indonesia
* **Mata Kuliah**: Mobile Programming (Semester 7)

---

## Fitur Utama & Layar Aplikasi

Aplikasi terdiri dari 3 layar utama yang terintegrasi penuh menggunakan **Navigation 2.8+ Type-Safe Routes**:

### 1. Layar Daftar Mahasiswa (`StudentListScreen`)
- **Daftar Profil Interactive**: Menampilkan 8 profil mahasiswa awal (`StudentSeed`).
- **Pencarian Real-time**: Filter nama atau NIM secara instan melalui pencarian input.
- **Dynamic Avatar**: Menampilkan foto profil kustom (`foto_profil`) untuk Fachri Reyhan dan avatar inisial nama untuk mahasiswa lainnya.
- **Navigasi Mudah**: Klik item mahasiswa untuk melihat detail, atau tekan tombol **`+ Tambah Profil`** (Extended FAB) untuk menambah profil baru.

### 2. Layar Detail Mahasiswa (`StudentDetailScreen`)
- Menampilkan rincian profil mahasiswa secara stabil berdasarkan ID unik (ID, NIM, Program Studi, Semester, dan Visibilitas Email).
- Tombol **`Edit Profil`** untuk melakukan pembaruan data secara langsung.
- Tombol **`Kembali ke daftar`** untuk navigasi kembali.

### 3. Layar Form Tambah & Edit (`StudentFormScreen`)
- **Dual Mode (Create & Edit)**: Satu form yang fleksibel untuk menambah mahasiswa baru maupun mengubah profil yang sudah ada.
- **State Management & Retention**: Penggunaan `rememberSaveable` agar data input tidak hilang saat orientasi layar diputar.
- **Logika Validasi Lengkap & NIM Unik**:
  - Nama: minimal 3 karakter.
  - NIM: wajib 10 digit angka & **divalidasi agar tidak boleh ganda** dengan NIM mahasiswa lain.
  - Email: validasi format ekspresi reguler (Regex).
  - Semester: jangkauan angka 1–14.
- **Fitur Reset & Snackbar Level Aplikasi**: Pesan sukses/gagal ditampilkan via `SnackbarHost` di level aplikasi sehingga notifikasi tetap terlihat saat kembali ke daftar.

---

## Arsitektur & Pola Desain

```text
[ Data Layer ]          [ ViewModel Layer ]         [ UI / Compose Layer ]
 Student.kt      --->    StudentViewModel.kt  --->   StudentListScreen.kt
 StudentSeed.kt           (StateFlow<List>)          StudentDetailScreen.kt
                                                     StudentFormScreen.kt
                                                              ^
[ Validation ]                                                |
 StudentValidator.kt -----------------------------------------+
```

1. **MVVM (Model-View-ViewModel)**: Pemisahan state UI dari *business logic*. `StudentViewModel` mengelola daftar `StateFlow<List<Student>>`.
2. **Type-Safe Navigation (Navigation 2.8+)**: Menggunakan `kotlinx.serialization` dengan rute terketik:
   - `StudentListRoute`
   - `StudentDetailRoute(val studentId: Int)`
   - `StudentFormRoute(val studentId: Int = 0)`
3. **Clean Unit Testing (`StudentValidatorTest.kt`)**: 5 Unit Test otomatis yang menguji validasi nama, NIM 10-digit, Regex email, batas semester, dan NIM unik.

---

## Tech Stack & Spesifikasi

| Komponen | Versi / Spesifikasi |
| :--- | :--- |
| **Bahasa Pemrograman** | Kotlin 2.2.10 |
| **UI Framework** | Jetpack Compose (BOM `2026.02.01`) |
| **Design System** | Material Design 3 (`androidx.compose.material3`) |
| **Navigasi** | Navigation Compose 2.8+ Type-Safe (`toRoute<T>()`) |
| **State Management** | StateFlow, `rememberSaveable`, ViewModel |
| **Testing** | JUnit 4 (`StudentValidatorTest`) |
| **Compile / Target SDK** | API Level 37 |
| **Minimum SDK** | API Level 24 (Android 7.0 Nougat) |
| **Gradle / AGP** | Gradle 9.6.0 / AGP 9.4.1 |

---

## Struktur Proyek

```text
0112523013_FachriReyhan_Project1/
├── app/src/main/java/com/example/a0112523013_fachrireyhan_p1/
│   ├── data/
│   │   └── Student.kt             # Data model Student & StudentSeed (8 profil)
│   ├── navigation/
│   │   └── AppRoutes.kt           # Type-safe Navigation Routes (Serializable)
│   ├── validation/
│   │   └── StudentValidator.kt    # Pure validation functions (Name, NIM, Email, Unique NIM)
│   ├── viewmodel/
│   │   └── StudentViewModel.kt    # In-memory repository & StateFlow management
│   ├── ui/
│   │   ├── ProfileComponents.kt   # Reusable stateless components (Avatar, Header, Card, Input)
│   │   ├── StudentListScreen.kt   # Screen 1: Lista & Pencarian Mahasiswa
│   │   ├── StudentDetailScreen.kt # Screen 2: Tampilan Detail Mahasiswa
│   │   └── StudentFormScreen.kt   # Screen 3: Form Tambah & Edit Mahasiswa
│   ├── ui/theme/                  # Color, Theme, & Typography
│   └── MainActivity.kt            # Entry Point App & NavHost Container
├── app/src/test/java/com/example/a0112523013_fachrireyhan_p1/
│   └── StudentValidatorTest.kt    # Unit Test Suite untuk fungsi validasi
├── ENVIRONMENT.md                 # Dokumentasi Lingkungan Pengembangan
└── README.md                      # Dokumentasi Utama Repository
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

4. **Menjalankan Unit Test**:
   - Buka terminal di Android Studio dan jalankan:
     ```bash
     ./gradlew test
     ```
   - Atau klik kanan file `StudentValidatorTest.kt` -> **Run 'StudentValidatorTest'**.

---

## Catatan Tambahan

Dokumen lengkap mengenai spesifikasi lingkungan pengembangan (*development environment*), pustaka dependencies, dan perintah CLI Gradle dapat dilihat pada file **[ENVIRONMENT.md](ENVIRONMENT.md)**.
