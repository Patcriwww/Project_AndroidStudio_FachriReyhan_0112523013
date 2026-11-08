package com.example.a0112523013_fachrireyhan_p1.state

import com.example.a0112523013_fachrireyhan_p1.data.Student
import com.example.a0112523013_fachrireyhan_p1.data.preferences.StudentSortOrder
import com.example.a0112523013_fachrireyhan_p1.data.preferences.UserSettings

data class StudentUiState(
    val students: List<Student> = emptyList(), // HANYA diisi lewat Room Flow.
    val searchQuery: String = "",
    val settings: UserSettings = UserSettings(),
    val loading: Boolean = true,
    val dataError: String? = null,
    val form: StudentFormUiState = StudentFormUiState()
)

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
    val isSaving: Boolean = false,
    val nameError: String? = null,
    val nimError: String? = null,
    val emailError: String? = null,
    val semesterError: String? = null,
    val isValid: Boolean = false
) {
    val isEditing: Boolean get() = editingId > 0
}

data class StudentListUiState(
    val query: String,
    val totalCount: Int,
    val visibleStudents: List<Student>
)
data class StudentDetailUiState(val student: Student?)

sealed interface StudentEvent {
    data class SearchChanged(val text: String) : StudentEvent
    data class FormOpened(val sessionKey: String, val editingId: Int) : StudentEvent
    data class NameChanged(val text: String) : StudentEvent
    data class NimChanged(val text: String) : StudentEvent
    data class EmailChanged(val text: String) : StudentEvent
    data class SemesterChanged(val delta: Int) : StudentEvent
    data class ShowEmailChanged(val value: Boolean) : StudentEvent
    data class ConfirmationChanged(val value: Boolean) : StudentEvent
    data class DarkModeChanged(val enabled: Boolean) : StudentEvent
    data class SortOrderChanged(val order: StudentSortOrder) : StudentEvent
    data object ResetClicked : StudentEvent
}

sealed interface StudentEventResult {
    data object None : StudentEventResult
    data class Saved(val message: String) : StudentEventResult
    data class Deleted(val message: String) : StudentEventResult
    data class Message(val text: String) : StudentEventResult
}
