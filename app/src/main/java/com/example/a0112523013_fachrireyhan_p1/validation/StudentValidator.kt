package com.example.a0112523013_fachrireyhan_p1.validation

// Mengembangkan fungsi validasi dari MainActivity.kt pada proyek pertama.
private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

fun validateName(name: String): String? = when {
    name.isBlank() -> "Nama wajib diisi"
    name.trim().length < 3 -> "Nama minimal 3 karakter"
    else -> null
}

fun validateNim(nim: String): String? = when {
    nim.isBlank() -> "NIM wajib diisi"
    !nim.all { it.isDigit() } -> "NIM hanya boleh angka"
    nim.length != 10 -> "NIM harus 10 digit (${nim.length}/10)"
    else -> null
}

fun validateEmail(email: String): String? = when {
    email.isBlank() -> "Email wajib diisi"
    !EMAIL_REGEX.matches(email.trim()) -> "Format email tidak valid"
    else -> null
}

fun validateSemester(semester: Int): String? = when {
    semester !in 1..14 -> "Semester harus antara 1 dan 14"
    else -> null
}

/** Nilai error null berarti data benar. Menghindari NIM ganda pada 8+ profil. */
fun validateUniqueNim(nim: String, otherNims: Iterable<String>): String? = when {
    otherNims.any { it == nim } -> "NIM sudah digunakan mahasiswa lain"
    else -> null
}
