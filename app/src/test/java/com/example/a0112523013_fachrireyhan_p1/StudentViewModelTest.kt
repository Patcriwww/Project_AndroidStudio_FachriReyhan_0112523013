package com.example.a0112523013_fachrireyhan_p1

import com.example.a0112523013_fachrireyhan_p1.data.Student
import com.example.a0112523013_fachrireyhan_p1.data.sortStudents
import com.example.a0112523013_fachrireyhan_p1.data.preferences.StudentSortOrder
import com.example.a0112523013_fachrireyhan_p1.state.StudentUiState
import org.junit.Assert.*
import org.junit.Test

/** Pengujian pure Kotlin. Persistence close/reopen perlu emulator Android. */
class StudentViewModelTest {
    private val students = listOf(
        Student(1, "Zahra", "0112523011", "z@example.com", 3, true),
        Student(2, "Alya", "0112523012", "a@example.com", 7, true)
    )

    @Test fun initialUiStateUsesRoomRatherThanHardcodedStudents() {
        assertTrue(StudentUiState().students.isEmpty())
        assertTrue(StudentUiState().loading)
    }

    @Test fun sortByName() {
        assertEquals(listOf("Alya", "Zahra"),
            sortStudents(students, StudentSortOrder.NAME_ASC).map { it.name })
    }

    @Test fun sortBySemesterDescending() {
        assertEquals(listOf(2, 1),
            sortStudents(students, StudentSortOrder.SEMESTER_DESC).map { it.id })
    }

    @Test fun immutableStateCopyDoesNotModifyPreviousSnapshot() {
        val before = StudentUiState(searchQuery = "")
        val after = before.copy(searchQuery = "Fachri")
        assertEquals("", before.searchQuery)
        assertEquals("Fachri", after.searchQuery)
    }
}
