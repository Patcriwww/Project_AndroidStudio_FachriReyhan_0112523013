# Edit Profile Screen - Interactive Compose Challenge

[![Android](https://img.shields.io/badge/Platform-Android-green.svg?style=flat&logo=android)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin_2.2.10-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-757575.svg?style=flat&logo=materialdesign)](https://m3.material.io)

Repository ini berisi **Tugas Interactive Compose State Challenge** untuk mata kuliah **Mobile Programming (Semester 7)**. Aplikasi ini mengimplementasikan layar **Edit Profile** interaktif menggunakan **Jetpack Compose** dengan penerapan **State Management**, **State Hoisting**, **Validasi Form Real-time**, **Auto Reset**, serta **Feedback Langsung via Snackbar**.

---

## Identitas Mahasiswa

* **Nama**: Fachri Reyhan
* **NIM**: 0112523013
* **Program Studi**: Informatika - Universitas Al Azhar Indonesia
* **Mata Kuliah**: Mobile Programming (Semester 7)

---

## Pemenuhan Rubrik & Persyaratan Tugas

| No | Persyaratan Tugas | Implementasi dalam Kode (`MainActivity.kt`) |
| :-: | :--- | :--- |
| **1** | **Minimal 2 Text Input** | 3 Text Input: **Nama Lengkap**, **NIM** (Khusus Angka, 10 Digit), dan **Email**. |
| **2** | **Kontrol Counter** | `CounterRow` untuk memilih **Semester** (1–14) dengan tombol `-` dan `+`. |
| **3** | **Switch & Checkbox** | `SwitchRow` untuk **Tampilkan Email** dan `CheckboxRow` untuk **Konfirmasi**. |
| **4** | **Validasi Form** | Validasi Nama (min 3 karakter), NIM (harus 10 angka), dan Email (regex format valid). |
| **5** | **Feedback Langsung** | Kartu **`SummaryCard`** yang memperbarui ringkasan data mahasiswa secara *real-time* & **Snackbar** info saat tombol Simpan diklik. |
| **6** | **State Hoisting & Stateless Composables** | Memisahkan logika state pada `EditProfileScreen` dan komponen *stateless*: `ProfileTextField`, `CounterRow`, `SwitchRow`, `CheckboxRow`, `SectionCard`, dan `SummaryCard`. |
| **7** | **Auto Reset & Tombol Reset** | **Tombol Reset** mandiri & **Auto Reset** seluruh isi form kembali ke kondisi awal setelah data berhasil disimpan. |

---

## Arsitektur & Komponen Utama

### 1. **Fungsi Validasi (Pure Functions)**
- `validateName(name: String)`: Memastikan nama tidak kosong dan minimal 3 karakter.
- `validateNim(nim: String)`: Memastikan NIM tidak kosong, hanya terdiri dari angka, dan tepat 10 digit.
- `validateEmail(email: String)`: Memastikan format email valid menggunakan ekspresi reguler (*Regex*).

### 2. **Komponen Stateless (State Hoisting Pattern)**
- **`ProfileTextField`**: Textfield stateless dengan dukungan pesan error real-time (*supportingText*).
- **`CounterRow`**: Kontrol counter stateless untuk pemilihan semester.
- **`SwitchRow`**: Baris switch stateless untuk visibilitas email.
- **`CheckboxRow`**: Baris checkbox stateless untuk konfirmasi persetujuan.
- **`SectionCard`**: Pembungkus card berlatar putih dengan judul bagian.
- **`SummaryCard`**: Card ringkasan data mahasiswa hasil rekapitulasi real-time.

### 3. **Stateful Container (`EditProfileScreen`)**
Mengelola seluruh state aplikasi secara aman menggunakan `rememberSaveable { mutableStateOf(...) }`:
- `name`: State nama mahasiswa.
- `nim`: State NIM mahasiswa.
- `email`: State email mahasiswa.
- `semester`: State angka semester (1-14).
- `showEmail`: State boolean visibilitas email.
- `confirmed`: State boolean konfirmasi persetujuan.
- `submitted`: State trigger pengujian validasi.

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
│   │   │   │   └── MainActivity.kt   # EditProfileScreen, Validation Logic, & Stateless Composables
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
   - Klik tombol **Run 'app'** (`Shift + F10`) atau jalankan `@Preview` **`EditProfileScreenPreview`**.

---

## Catatan Tambahan

Dokumen lengkap mengenai spesifikasi lingkungan pengembangan (*development environment*), pustaka dependencies, dan perintah CLI Gradle dapat dilihat pada file **[ENVIRONMENT.md](ENVIRONMENT.md)**.
