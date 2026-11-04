package com.example.a0112523013_fachrireyhan_p1

import com.example.a0112523013_fachrireyhan_p1.state.StudentEvent
import com.example.a0112523013_fachrireyhan_p1.state.StudentEventResult
import com.example.a0112523013_fachrireyhan_p1.viewmodel.StudentViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/** Unit tests P6. Ini bukan pengganti pengujian rotasi perangkat yang sesungguhnya. */
class StudentViewModelTest {
    @Test
    fun eightStudentsAvailableInitially() {
        assertEquals(8, StudentViewModel().uiState.value.students.size)
    }

    @Test
    fun searchEventChangesImmutableState() {
        val model = StudentViewModel()
        val oldState = model.uiState.value
        model.onEvent(StudentEvent.SearchChanged("Fachri"))
        assertEquals("Fachri", model.uiState.value.searchQuery)
        assertEquals("", oldState.searchQuery)
    }

    @Test
    fun sameBackstackEntryDoesNotResetDraft() {
        val model = StudentViewModel()
        model.onEvent(StudentEvent.FormOpened("entry-42", 1))
        model.onEvent(StudentEvent.NameChanged("Fachri Reyhan Edit"))
        model.onEvent(StudentEvent.SemesterChanged(1))
        // Simulasikan event composable masuk kembali setelah activity recreation.
        model.onEvent(StudentEvent.FormOpened("entry-42", 1))
        assertEquals("Fachri Reyhan Edit", model.uiState.value.form.name)
        assertEquals(8, model.uiState.value.form.semester)
    }

    @Test
    fun saveValidNewStudentUpdatesState() {
        val model = StudentViewModel()
        model.onEvent(StudentEvent.FormOpened("entry-new", 0))
        model.onEvent(StudentEvent.NameChanged("Mahasiswa Baru"))
        model.onEvent(StudentEvent.NimChanged("0112523999"))
        model.onEvent(StudentEvent.EmailChanged("baru@example.com"))
        model.onEvent(StudentEvent.ConfirmationChanged(true))
        val result = model.onEvent(StudentEvent.SaveClicked)
        assertTrue(result is StudentEventResult.Saved)
        assertEquals(9, model.uiState.value.students.size)
        assertEquals("Mahasiswa Baru", model.uiState.value.students.last().name)
    }

    @Test
    fun invalidNimIsRejectedWithError() {
        val model = StudentViewModel()
        model.onEvent(StudentEvent.FormOpened("entry-new", 0))
        model.onEvent(StudentEvent.NameChanged("Mahasiswa Baru"))
        model.onEvent(StudentEvent.NimChanged("abc"))
        model.onEvent(StudentEvent.EmailChanged("baru@example.com"))
        model.onEvent(StudentEvent.ConfirmationChanged(true))
        assertTrue(model.onEvent(StudentEvent.SaveClicked) is StudentEventResult.Message)
        assertFalse(model.uiState.value.form.isValid)
        assertEquals(8, model.uiState.value.students.size)
    }

    @Test
    fun resetRestoresOriginalValues() {
        val model = StudentViewModel()
        model.onEvent(StudentEvent.FormOpened("entry-edit", 1))
        model.onEvent(StudentEvent.NameChanged("Sementara"))
        model.onEvent(StudentEvent.ResetClicked)
        assertEquals("Fachri Reyhan", model.uiState.value.form.name)
        assertFalse(model.uiState.value.form.confirmed)
    }
    @Test
    fun unregisteredStudentIdProducesInvalidSelectionState() {
        val model = StudentViewModel()
        model.onEvent(StudentEvent.FormOpened("missing", 99999))
        assertFalse(model.uiState.value.form.existingStudentFound)
        assertTrue(model.onEvent(StudentEvent.SaveClicked) is StudentEventResult.Message)
    }

    @Test
    fun updatedStateDoesNotMutatePreviousSnapshot() {
        val model = StudentViewModel()
        val before = model.uiState.value
        model.onEvent(StudentEvent.SearchChanged("zz-not-found-123"))
        assertEquals("", before.searchQuery)
        assertEquals("zz-not-found-123", model.uiState.value.searchQuery)
    }

}
