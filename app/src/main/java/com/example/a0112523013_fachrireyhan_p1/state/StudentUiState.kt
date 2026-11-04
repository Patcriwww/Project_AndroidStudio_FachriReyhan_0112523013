package com.example.a0112523013_fachrireyhan_p1.state

import com.example.a0112523013_fachrireyhan_p1.data.Student
import com.example.a0112523013_fachrireyhan_p1.data.StudentSeed

/** Immutable, single source of truth untuk seluruh layar aplikasi. */
data class StudentUiState(
    val students: List<Student> = StudentSeed.create(),
    val searchQuery: String = "",
    val form: StudentFormUiState = StudentFormUiState()
)

/** Setiap properti memakai val; perubahan dibuat melalui copy() di ViewModel. */
data class StudentFormUiState(
    val sessionKey: String? = null,
    val editingId: Int = 0,
    val existingStudentFound: Boolean = true,
    val name: String = "",
    val nim: String = "",
    val email: String = "",
    val semester: Int = 1,
    val showEmail: Boolean = true,
    val confirmed: Boolean = false,
    val submitted: Boolean = false,
    val nameError: String? = null,
    val nimError: String? = null,
    val emailError: String? = null,
    val semesterError: String? = null,
    val isValid: Boolean = false
) {
    val isEditing: Boolean get() = editingId > 0
}

/** Model yang khusus diberikan kepada layar list; tidak memuat ViewModel. */
data class StudentListUiState(
    val query: String,
    val totalCount: Int,
    val visibleStudents: List<Student>
)

/** Model yang khusus diberikan kepada layar detail. */
data class StudentDetailUiState(val student: Student?)

/** Semua aksi pengguna diteruskan dari UI menuju ViewModel. */
sealed interface StudentEvent {
    data class SearchChanged(val text: String) : StudentEvent
    data class FormOpened(val sessionKey: String, val editingId: Int) : StudentEvent
    data class NameChanged(val text: String) : StudentEvent
    data class NimChanged(val text: String) : StudentEvent
    data class EmailChanged(val text: String) : StudentEvent
    data class SemesterChanged(val delta: Int) : StudentEvent
    data class ShowEmailChanged(val value: Boolean) : StudentEvent
    data class ConfirmationChanged(val value: Boolean) : StudentEvent
    data object SaveClicked : StudentEvent
    data object ResetClicked : StudentEvent
}

sealed interface StudentEventResult {
    data object None : StudentEventResult
    data class Saved(val message: String) : StudentEventResult
    data class Message(val text: String) : StudentEventResult
}
