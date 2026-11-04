package com.example.a0112523013_fachrireyhan_p1.ui.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.a0112523013_fachrireyhan_p1.state.StudentDetailUiState
import com.example.a0112523013_fachrireyhan_p1.state.StudentEvent
import com.example.a0112523013_fachrireyhan_p1.state.StudentEventResult
import com.example.a0112523013_fachrireyhan_p1.state.StudentListUiState
import com.example.a0112523013_fachrireyhan_p1.ui.StudentDetailScreen
import com.example.a0112523013_fachrireyhan_p1.ui.StudentFormScreen
import com.example.a0112523013_fachrireyhan_p1.ui.StudentListScreen
import com.example.a0112523013_fachrireyhan_p1.viewmodel.StudentViewModel

/** Route mengamati ViewModel; Screen bersifat stateless (UiState + callbacks). */
@Composable
fun StudentListRouteContent(
    model: StudentViewModel,
    onStudentClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    val state by model.uiState.collectAsStateWithLifecycle()
    val visible = remember(state.students, state.searchQuery) {
        state.students.filter { student ->
            student.name.contains(state.searchQuery, ignoreCase = true) ||
                student.nim.contains(state.searchQuery)
        }
    }
    StudentListScreen(
        state = StudentListUiState(
            query = state.searchQuery,
            totalCount = state.students.size,
            visibleStudents = visible
        ),
        onSearchChanged = { model.onEvent(StudentEvent.SearchChanged(it)) },
        onStudentClick = onStudentClick,
        onAddClick = onAddClick
    )
}

@Composable
fun StudentDetailRouteContent(
    model: StudentViewModel,
    studentId: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    val state by model.uiState.collectAsStateWithLifecycle()
    StudentDetailScreen(
        state = StudentDetailUiState(
            student = state.students.firstOrNull { it.id == studentId }
        ),
        studentId = studentId,
        onBack = onBack,
        onEdit = onEdit
    )
}

@Composable
fun StudentFormRouteContent(
    model: StudentViewModel,
    sessionKey: String,
    editingId: Int,
    onBack: () -> Unit,
    onSaved: (String) -> Unit,
    onMessage: (String) -> Unit
) {
    val state by model.uiState.collectAsStateWithLifecycle()
    // NavBackStackEntry.id tetap sama ketika rotasi, sehingga draft tidak di-reset.
    LaunchedEffect(sessionKey, editingId) {
        model.onEvent(StudentEvent.FormOpened(sessionKey, editingId))
    }
    if (state.form.sessionKey == sessionKey) {
        StudentFormScreen(
            state = state.form,
            onEvent = { event ->
                when (val result = model.onEvent(event)) {
                    is StudentEventResult.Message -> onMessage(result.text)
                    is StudentEventResult.Saved -> onSaved(result.message)
                    StudentEventResult.None -> Unit
                }
            },
            onBack = onBack,
            onSave = {
                when (val result = model.onEvent(StudentEvent.SaveClicked)) {
                    is StudentEventResult.Saved -> onSaved(result.message)
                    is StudentEventResult.Message -> onMessage(result.text)
                    StudentEventResult.None -> Unit
                }
            }
        )
    } else {
        // Loading singkat saat menginisialisasi draft atau setelah navigasi baru.
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
