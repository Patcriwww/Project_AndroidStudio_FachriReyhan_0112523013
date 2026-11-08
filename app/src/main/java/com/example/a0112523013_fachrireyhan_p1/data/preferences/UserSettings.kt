package com.example.a0112523013_fachrireyhan_p1.data.preferences

enum class StudentSortOrder(val label: String) {
    ID_ASC("Urutan asli (ID)"),
    NAME_ASC("Nama (A-Z)"),
    SEMESTER_DESC("Semester (tertinggi dulu)")
}

data class UserSettings(
    val darkMode: Boolean = false,
    val sortOrder: StudentSortOrder = StudentSortOrder.ID_ASC
)
