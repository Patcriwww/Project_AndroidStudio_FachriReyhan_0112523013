# Project 1 - Mobile Programming

[![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=flat&logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin_2.2.10-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-757575.svg?style=flat&logo=materialdesign)](https://m3.material.io)

Repository ini berisi **Project 1** untuk mata kuliah **Mobile Programming (Semester 7)**. Proyek ini dibangun menggunakan **Android Studio**, bahasa pemrograman **Kotlin**, dan framework antarmuka **Jetpack Compose**.

---

## Identitas Mahasiswa

* **Nama**: Fachri Reyhan
* **NIM**: 0112523013
* **Mata Kuliah**: Pemrograman Bergerak
* **Semester**: 7

---

## Fitur & Keunggulan App

- **Modern Declarative UI**: Memanfaatkan Jetpack Compose dan Material Design 3.
- **Edge-to-Edge Layout**: Pengalaman tampilan penuh (*immersive experience*) dengan dukungan `enableEdgeToEdge()`.
- **Dynamic Theme**: Mendukung kustomisasi Skema Warna (*Light & Dark Theme*) melalui paket `ui.theme`.
- **Modern Build Configuration**: Menggunakan Gradle Version Catalog (`libs.versions.toml`) dan Kotlin 2.2.

---

## Tech Stack & Spesifikasi

| Komponen | Versi / Spesifikasi |
| :--- | :--- |
| **Bahasa Pemrograman** | Kotlin 2.2.10 |
| **UI Framework** | Jetpack Compose (BOM `2026.02.01`) |
| **Design System** | Material Design 3 (`androidx.compose.material3`) |
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
│   │   │   │   ├── ui/theme/         # Tema, Warna, & Tipografi App
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   └── Type.kt
│   │   │   │   └── MainActivity.kt   # Activity Utama & Entry Point Compose
│   │   │   └── AndroidManifest.xml   # Manifest Aplikasi
│   └── build.gradle.kts              # Konfigurasi Build Module App
├── gradle/
│   ├── libs.versions.toml            # Gradle Version Catalog (Dependency Management)
│   └── wrapper/                      # Gradle Wrapper Properties
├── ENVIRONMENT.md                    # Catatan Lengkap Environment Proyek
└── README.md                         # Dokumentasi Repository GitHub
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
