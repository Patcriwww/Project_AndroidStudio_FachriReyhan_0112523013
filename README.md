# 📱 Project 1 - Aplikasi Profil Mahasiswa (Mobile Programming)

[![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=flat&logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin_2.2.10-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-757575.svg?style=flat&logo=materialdesign)](https://m3.material.io)

Repository ini berisi **Project 1 - Aplikasi Profil Mahasiswa** untuk mata kuliah **Mobile Programming (Semester 7)**. Aplikasi ini dibuat menggunakan **Android Studio**, **Kotlin**, dan **Jetpack Compose** dengan menerapkan arsitektur komponen deklaratif yang rapi dan modular.

---

## Identitas Mahasiswa

* **Nama**: Fachri Reyhan
* **NIM**: 0112523013
* **Program Studi**: Informatik - Universitas Al Azhar Indonesia
* **Mata Kuliah**: Mobile Programming (Semester 7)

---

## Fitur & Komponen Aplikasi (`MainActivity.kt`)

Aplikasi terdiri dari layar profil interaktif yang tersusun atas beberapa komponen kustom (*Reusable Custom Composables*):

1. **`ProfileHeader`**:
   - Menampilkan foto profil melingkar (`foto_profil`) menggunakan `Image` & `CircleShape`.
   - Menampilkan **Nama Mahasiswa** dan **Program Studi / Universitas** dengan latar belakang warna utama (*Primary Color*).

2. **`InfoItem` (Reusable Component)**:
   - Komponen terisolasi berbentuk `Row` (ikon di sebelah kiri, label dan nilai di sebelah kanan).
   - Menampilkan informasi detail kampus seperti:
     - **Kampus**: Universitas Al Azhar Indonesia (`Icons.Default.Home`)
     - **NIM**: 0112523013 (`Icons.Default.Person`)
     - **Email**: fachrireyhan@student.uai.ac.id (`Icons.Default.Email`)
     - **Lokasi**: Jakarta Selatan (`Icons.Default.Place`)

3. **`ProfileScreen` (State Management Interaktif)**:
   - Menggabungkan `ProfileHeader`, `Card` berisi daftar `InfoItem`, dan `Button` interaktif.
   - Menggunakan state `remember { mutableStateOf(false) }` untuk mengubah status tombol antara **"Hubungi Saya"** dan **"Terhubung ✓"** saat diklik.

4. **`ProfileScreenPreview`**:
   - `@Preview` Composable untuk melihat tampilan UI secara langsung (*live preview*) di Android Studio tanpa perlu menjalankan emulator.

---

## Tech Stack & Spesifikasi

| Komponen | Versi / Spesifikasi |
| :--- | :--- |
| **Bahasa Pemrograman** | Kotlin 2.2.10 |
| **UI Framework** | Jetpack Compose (BOM `2026.02.01`) |
| **Design System** | Material Design 3 (`androidx.compose.material3`) |
| **Icons Library** | `androidx.compose.material:material-icons-core` |
| **Compile / Target SDK** | API Level 37 |
| **Minimum SDK** | API Level 24 (Android 7.0 Nougat) |
| **Gradle / AGP** | Gradle 9.6.0 / AGP 9.4.1 |
| **Java Compatibility** | Java 11 |

---

## Struktur Proyek

```text
0112523013_FachriReyhan_Project1/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/a0112523013_fachrireyhan_p1/
│   │   │   │   ├── ui/theme/         # Custom Material3 Color Theme & Typography
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   └── Type.kt
│   │   │   │   └── MainActivity.kt   # ProfileScreen, ProfileHeader, InfoItem, & App Entry
│   │   │   ├── res/
│   │   │   │   └── drawable/         # Asset Gambar (foto_profil, dll)
│   │   │   └── AndroidManifest.xml   # Manifest Aplikasi
│   └── build.gradle.kts              # Module Build Script & Dependencies
├── gradle/
│   ├── libs.versions.toml            # Gradle Version Catalog
│   └── wrapper/                      # Gradle Wrapper Configuration
├── ENVIRONMENT.md                    # Dokumentasi Lingkungan Pengembangan
└── README.md                         # Dokumentasi Utama Repository
```

---

## Cara Menjalankan Proyek (Getting Started)

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
   - Klik tombol **Run 'app'** (`Shift + F10`) atau tombol ▶️ pada toolbar Android Studio.

---

## Catatan Tambahan

Dokumen lengkap mengenai spesifikasi lingkungan pengembangan (*development environment*), pustaka dependencies, dan perintah CLI Gradle dapat dilihat pada file **[ENVIRONMENT.md](ENVIRONMENT.md)**.
