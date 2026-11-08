package com.example.a0112523013_fachrireyhan_p1.data

import com.example.a0112523013_fachrireyhan_p1.data.preferences.StudentSortOrder

fun sortStudents(students: List<Student>, order: StudentSortOrder): List<Student> = when (order) {
    StudentSortOrder.ID_ASC -> students.sortedBy { it.id }
    StudentSortOrder.NAME_ASC -> students.sortedWith(compareBy({ it.name.lowercase() }, { it.id }))
    StudentSortOrder.SEMESTER_DESC -> students.sortedWith(
        compareByDescending<Student> { it.semester }.thenBy { it.id }
    )
}
