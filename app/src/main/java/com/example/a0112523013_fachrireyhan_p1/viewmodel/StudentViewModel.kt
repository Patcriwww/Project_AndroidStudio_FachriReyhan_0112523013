package com.example.a0112523013_fachrireyhan_p1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a0112523013_fachrireyhan_p1.data.Student
import com.example.a0112523013_fachrireyhan_p1.state.StudentEvent
import com.example.a0112523013_fachrireyhan_p1.state.StudentEventResult
import com.example.a0112523013_fachrireyhan_p1.state.StudentFormUiState
import com.example.a0112523013_fachrireyhan_p1.state.StudentUiState
import com.example.a0112523013_fachrireyhan_p1.validation.validateEmail
import com.example.a0112523013_fachrireyhan_p1.validation.validateName
import com.example.a0112523013_fachrireyhan_p1.validation.validateNim
import com.example.a0112523013_fachrireyhan_p1.validation.validateSemester
import com.example.a0112523013_fachrireyhan_p1.validation.validateUniqueNim
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Presentation logic Pertemuan 6.
 * Tidak menyimpan NavController, Activity, SnackbarHostState, atau state Compose.
 * ViewModel activity-scoped bertahan saat activity mengalami perubahan konfigurasi.
 */
class StudentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(StudentUiState())
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    /** Satu pintu untuk event pengguna; efek navigasi dikembalikan ke UI. */
    fun onEvent(event: StudentEvent): StudentEventResult = when (event) {
        is StudentEvent.SearchChanged -> {
            _uiState.update { it.copy(searchQuery = event.text) }
            StudentEventResult.None
        }
        is StudentEvent.FormOpened -> {
            openForm(event.sessionKey, event.editingId)
            StudentEventResult.None
        }
        is StudentEvent.NameChanged -> updateForm { it.copy(name = event.text) }
        is StudentEvent.NimChanged -> updateForm { it.copy(nim = event.text.take(10)) }
        is StudentEvent.EmailChanged -> updateForm { it.copy(email = event.text) }
        is StudentEvent.SemesterChanged -> updateForm {
            it.copy(semester = (it.semester + event.delta).coerceIn(1, 14))
        }
        is StudentEvent.ShowEmailChanged -> updateForm { it.copy(showEmail = event.value) }
        is StudentEvent.ConfirmationChanged -> updateForm { it.copy(confirmed = event.value) }
        StudentEvent.SaveClicked -> saveForm()
        StudentEvent.ResetClicked -> {
            resetForm()
            StudentEventResult.Message("Form berhasil direset")
        }
    }

    /** Session key = NavBackStackEntry.id, supaya form tidak ter-reset ketika rotasi. */
    private fun openForm(sessionKey: String, editingId: Int) {
        _uiState.update { state ->
            if (state.form.sessionKey == sessionKey) state
            else state.copy(form = newForm(state, sessionKey, editingId))
        }
    }

    private fun newForm(
        state: StudentUiState,
        sessionKey: String?,
        editingId: Int
    ): StudentFormUiState {
        val original = state.students.firstOrNull { it.id == editingId }
        val newForm = StudentFormUiState(
            sessionKey = sessionKey,
            editingId = editingId,
            existingStudentFound = editingId == 0 || original != null,
            name = original?.name.orEmpty(),
            nim = original?.nim.orEmpty(),
            email = original?.email.orEmpty(),
            semester = original?.semester ?: 1,
            showEmail = original?.showEmail ?: true
        )
        return validateForm(state, newForm)
    }

    private fun updateForm(transform: (StudentFormUiState) -> StudentFormUiState): StudentEventResult {
        _uiState.update { state ->
            if (state.form.sessionKey == null) state
            else state.copy(form = validateForm(state, transform(state.form)))
        }
        return StudentEventResult.None
    }

    /** Semua error dan isValid dihitung dalam ViewModel, bukan Composable. */
    private fun validateForm(
        state: StudentUiState,
        draft: StudentFormUiState
    ): StudentFormUiState {
        val nameError = validateName(draft.name)
        val nimError = validateNim(draft.nim)
            ?: validateUniqueNim(
                draft.nim,
                state.students.filter { it.id != draft.editingId }.map { it.nim }
            )
        val emailError = validateEmail(draft.email)
        val semesterError = validateSemester(draft.semester)
        val shouldShowName = draft.name.isNotEmpty() || draft.submitted
        val shouldShowNim = draft.nim.isNotEmpty() || draft.submitted
        val shouldShowEmail = draft.email.isNotEmpty() || draft.submitted
        return draft.copy(
            nameError = nameError.takeIf { shouldShowName },
            nimError = nimError.takeIf { shouldShowNim },
            emailError = emailError.takeIf { shouldShowEmail },
            semesterError = semesterError.takeIf { draft.submitted },
            isValid = nameError == null && nimError == null &&
                emailError == null && semesterError == null && draft.existingStudentFound
        )
    }

    private fun resetForm() {
        _uiState.update { state ->
            val current = state.form
            if (current.sessionKey == null) state
            else state.copy(form = newForm(state, current.sessionKey, current.editingId))
        }
    }

    private fun saveForm(): StudentEventResult {
        val previous = _uiState.value
        val draft = validateForm(previous, previous.form.copy(submitted = true))
        _uiState.update { it.copy(form = draft) }
        if (draft.sessionKey == null) return StudentEventResult.Message("Form belum siap")
        if (!draft.existingStudentFound) return StudentEventResult.Message("Mahasiswa tidak ditemukan")
        if (!draft.isValid) return StudentEventResult.Message("Periksa kembali data Anda")
        if (!draft.confirmed) return StudentEventResult.Message("Tolong konfirmasi terlebih dahulu")

        val currentStudents = _uiState.value.students
        val id = if (draft.isEditing) draft.editingId
                 else (currentStudents.maxOfOrNull { it.id } ?: 0) + 1
        val saved = Student(
            id = id,
            name = draft.name.trim(),
            nim = draft.nim,
            email = draft.email.trim(),
            semester = draft.semester,
            showEmail = draft.showEmail
        )
        _uiState.update { state ->
            state.copy(students = if (draft.isEditing) {
                state.students.map { if (it.id == id) saved else it }
            } else state.students + saved)
        }
        return StudentEventResult.Saved(
            if (draft.isEditing) "Profil ${saved.name} berhasil diperbarui"
            else "Mahasiswa ${saved.name} berhasil ditambahkan"
        )
    }
}
