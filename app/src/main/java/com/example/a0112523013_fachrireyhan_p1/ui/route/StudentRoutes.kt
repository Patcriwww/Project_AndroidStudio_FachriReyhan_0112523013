package com.example.a0112523013_fachrireyhan_p1.ui.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.a0112523013_fachrireyhan_p1.data.sortStudents
import com.example.a0112523013_fachrireyhan_p1.state.StudentDetailUiState
import com.example.a0112523013_fachrireyhan_p1.state.StudentEvent
import com.example.a0112523013_fachrireyhan_p1.state.StudentEventResult
import com.example.a0112523013_fachrireyhan_p1.state.StudentListUiState
import com.example.a0112523013_fachrireyhan_p1.ui.StudentDetailScreen
import com.example.a0112523013_fachrireyhan_p1.ui.StudentFormScreen
import com.example.a0112523013_fachrireyhan_p1.ui.StudentListScreen
import com.example.a0112523013_fachrireyhan_p1.ui.StudentSettingsScreen
import com.example.a0112523013_fachrireyhan_p1.viewmodel.StudentViewModel
import kotlinx.coroutines.launch

@Composable
fun StudentListRouteContent(
    model: StudentViewModel,
    onStudentClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val state by model.uiState.collectAsStateWithLifecycle()
    if (state.loading) {
        LoadingState()
        return
    }
    if (state.dataError != null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(state.dataError ?: "Terjadi kesalahan")
        }
        return
    }
    val visible = remember(state.students, state.searchQuery, state.settings.sortOrder) {
        val filtered = state.students.filter { student ->
            student.name.contains(state.searchQuery, ignoreCase = true) ||
                student.nim.contains(state.searchQuery)
        }
        sortStudents(filtered, state.settings.sortOrder)
    }
    StudentListScreen(
        state = StudentListUiState(
            query = state.searchQuery,
            totalCount = state.students.size,
            visibleStudents = visible
        ),
        onSearchChanged = { model.onEvent(StudentEvent.SearchChanged(it)) },
        onStudentClick = onStudentClick,
        onAddClick = onAddClick,
        onSettingsClick = onSettingsClick
    )
}

@Composable
fun StudentDetailRouteContent(
    model: StudentViewModel,
    studentId: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDeleted: (String) -> Unit,
    onMessage: (String) -> Unit
) {
    val state by model.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    if (state.loading) {
        LoadingState()
        return
    }
    StudentDetailScreen(
        state = StudentDetailUiState(state.students.firstOrNull { it.id == studentId }),
        studentId = studentId,
        onBack = onBack,
        onEdit = onEdit,
        onDelete = { id ->
            scope.launch {
                when (val result = model.deleteStudent(id)) {
                    is StudentEventResult.Deleted -> onDeleted(result.message)
                    is StudentEventResult.Message -> onMessage(result.text)
                    else -> Unit
                }
            }
        }
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
    val scope = rememberCoroutineScope()
    LaunchedEffect(sessionKey, editingId, state.loading) {
        if (!state.loading) model.onEvent(StudentEvent.FormOpened(sessionKey, editingId))
    }
    if (state.loading || state.form.sessionKey != sessionKey) {
        LoadingState()
        return
    }
    StudentFormScreen(
        state = state.form,
        onEvent = { event ->
            when (val result = model.onEvent(event)) {
                is StudentEventResult.Message -> onMessage(result.text)
                else -> Unit
            }
        },
        onBack = onBack,
        onSave = {
            scope.launch {
                when (val result = model.saveForm()) {
                    is StudentEventResult.Saved -> onSaved(result.message)
                    is StudentEventResult.Message -> onMessage(result.text)
                    else -> Unit
                }
            }
        }
    )
}

@Composable
fun StudentSettingsRouteContent(model: StudentViewModel, onBack: () -> Unit) {
    val state by model.uiState.collectAsStateWithLifecycle()
    StudentSettingsScreen(
        state = state.settings,
        onDarkModeChange = { model.onEvent(StudentEvent.DarkModeChanged(it)) },
        onSortOrderChange = { model.onEvent(StudentEvent.SortOrderChanged(it)) },
        onBack = onBack
    )
}

@Composable
private fun LoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
