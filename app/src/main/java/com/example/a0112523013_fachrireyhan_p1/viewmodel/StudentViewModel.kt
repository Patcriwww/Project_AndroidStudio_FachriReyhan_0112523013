package com.example.a0112523013_fachrireyhan_p1.viewmodel

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a0112523013_fachrireyhan_p1.data.Student
import com.example.a0112523013_fachrireyhan_p1.data.StudentRepository
import com.example.a0112523013_fachrireyhan_p1.data.local.StudentDatabase
import com.example.a0112523013_fachrireyhan_p1.data.preferences.UserPreferencesRepository
import com.example.a0112523013_fachrireyhan_p1.state.StudentEvent
import com.example.a0112523013_fachrireyhan_p1.state.StudentEventResult
import com.example.a0112523013_fachrireyhan_p1.state.StudentFormUiState
import com.example.a0112523013_fachrireyhan_p1.state.StudentUiState
import com.example.a0112523013_fachrireyhan_p1.validation.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** ViewModel TIDAK mengakses DAO, NavController, Activity, atau Composable. */
class StudentViewModel(
    private val studentsRepository: StudentRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(StudentUiState())
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    init {
        // Room -> Repository -> ViewModel -> UiState (Flow yang berkelanjutan).
        viewModelScope.launch {
            studentsRepository.students
                .catch { error ->
                    _uiState.update {
                        it.copy(loading = false, dataError = "Gagal membaca database: ${error.message}")
                    }
                }.collect { students ->
                    _uiState.update { state ->
                        state.copy(students = students, loading = false, dataError = null)
                    }
                }
        }
        // DataStore -> Repository -> ViewModel -> UiState.
        viewModelScope.launch {
            preferencesRepository.settings
                .catch { error ->
                    _uiState.update { it.copy(dataError = "Gagal membaca pengaturan: ${error.message}") }
                }.collect { settings ->
                    _uiState.update { it.copy(settings = settings) }
                }
        }
    }

    /** Minimal 3 event diproses melalui ViewModel, tanpa logika bisnis pada Screen. */
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
        is StudentEvent.DarkModeChanged -> {
            viewModelScope.launch {
                runCatching { preferencesRepository.setDarkMode(event.enabled) }
                    .onFailure { error ->
                        _uiState.update { it.copy(dataError = "Gagal menyimpan dark mode: ${error.message}") }
                    }
            }
            StudentEventResult.None
        }
        is StudentEvent.SortOrderChanged -> {
            viewModelScope.launch {
                runCatching { preferencesRepository.setSortOrder(event.order) }
                    .onFailure { error ->
                        _uiState.update { it.copy(dataError = "Gagal menyimpan urutan: ${error.message}") }
                    }
            }
            StudentEventResult.None
        }
        StudentEvent.ResetClicked -> {
            resetForm()
            StudentEventResult.Message("Form berhasil direset")
        }
    }

    private fun openForm(sessionKey: String, editingId: Int) {
        _uiState.update { state ->
            if (state.form.sessionKey == sessionKey) state
            else state.copy(form = newForm(state, sessionKey, editingId))
        }
    }

    private fun newForm(state: StudentUiState, key: String?, editingId: Int): StudentFormUiState {
        val original = state.students.firstOrNull { it.id == editingId }
        return validateForm(state, StudentFormUiState(
            sessionKey = key,
            editingId = editingId,
            existingStudentFound = editingId == 0 || original != null,
            name = original?.name.orEmpty(),
            nim = original?.nim.orEmpty(),
            email = original?.email.orEmpty(),
            semester = original?.semester ?: 1,
            showEmail = original?.showEmail ?: true
        ))
    }

    private fun updateForm(transform: (StudentFormUiState) -> StudentFormUiState): StudentEventResult {
        _uiState.update { state ->
            if (state.form.sessionKey == null || state.form.isSaving) state
            else state.copy(form = validateForm(state, transform(state.form)))
        }
        return StudentEventResult.None
    }

    private fun validateForm(state: StudentUiState, form: StudentFormUiState): StudentFormUiState {
        val nameError = validateName(form.name)
        val nimError = validateNim(form.nim) ?: validateUniqueNim(
            form.nim, state.students.filter { it.id != form.editingId }.map { it.nim }
        )
        val emailError = validateEmail(form.email)
        val semesterError = validateSemester(form.semester)
        return form.copy(
            nameError = nameError.takeIf { form.name.isNotEmpty() || form.submitted },
            nimError = nimError.takeIf { form.nim.isNotEmpty() || form.submitted },
            emailError = emailError.takeIf { form.email.isNotEmpty() || form.submitted },
            semesterError = semesterError.takeIf { form.submitted },
            isValid = nameError == null && nimError == null && emailError == null &&
                semesterError == null && form.existingStudentFound
        )
    }

    private fun resetForm() {
        _uiState.update { state ->
            val draft = state.form
            if (draft.sessionKey == null || draft.isSaving) state
            else state.copy(form = newForm(state, draft.sessionKey, draft.editingId))
        }
    }

    /** Save ke Room harus suspend. UI baru navigasi setelah operasi DB benar-benar berhasil. */
    suspend fun saveForm(): StudentEventResult {
        val before = _uiState.value
        if (before.form.isSaving) return StudentEventResult.Message("Penyimpanan sedang berlangsung")
        val draft = validateForm(before, before.form.copy(submitted = true))
        _uiState.update { it.copy(form = draft) }
        if (draft.sessionKey == null) return StudentEventResult.Message("Form belum siap")
        if (!draft.existingStudentFound) return StudentEventResult.Message("Mahasiswa tidak ditemukan")
        if (!draft.isValid) return StudentEventResult.Message("Periksa kembali data Anda")
        if (!draft.confirmed) return StudentEventResult.Message("Tolong konfirmasi terlebih dahulu")

        _uiState.update { it.copy(form = it.form.copy(isSaving = true)) }
        return try {
            val student = Student(
                id = draft.editingId,
                name = draft.name.trim(),
                nim = draft.nim,
                email = draft.email.trim(),
                semester = draft.semester,
                showEmail = draft.showEmail
            )
            if (draft.isEditing) {
                if (!studentsRepository.edit(student)) {
                    return StudentEventResult.Message("Mahasiswa tidak ditemukan")
                }
            } else {
                studentsRepository.add(student)
            }
            StudentEventResult.Saved(
                if (draft.isEditing) "Profil ${student.name} berhasil diperbarui"
                else "Mahasiswa ${student.name} berhasil ditambahkan"
            )
        } catch (_: SQLiteConstraintException) {
            StudentEventResult.Message("NIM sudah digunakan mahasiswa lain")
        } catch (error: Exception) {
            StudentEventResult.Message("Gagal menyimpan data: ${error.message}")
        } finally {
            _uiState.update { current ->
                if (current.form.sessionKey == draft.sessionKey)
                    current.copy(form = current.form.copy(isSaving = false))
                else current
            }
        }
    }

    suspend fun deleteStudent(id: Int): StudentEventResult = try {
        if (studentsRepository.delete(id)) {
            StudentEventResult.Deleted("Mahasiswa berhasil dihapus")
        } else StudentEventResult.Message("Mahasiswa tidak ditemukan")
    } catch (error: Exception) {
        StudentEventResult.Message("Gagal menghapus: ${error.message}")
    }
}

/** Factory di luar Composable, hanya dibuat sekali oleh Activity UI. */
class StudentViewModelFactory(context: Context) : ViewModelProvider.Factory {
    private val appContext = context.applicationContext

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(StudentViewModel::class.java))
        val database = StudentDatabase.getInstance(appContext)
        return StudentViewModel(
            studentsRepository = StudentRepository(database.studentDao()),
            preferencesRepository = UserPreferencesRepository(appContext)
        ) as T
    }
}
