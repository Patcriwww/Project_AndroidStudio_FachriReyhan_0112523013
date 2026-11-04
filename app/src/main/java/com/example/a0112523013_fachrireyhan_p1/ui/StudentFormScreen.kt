package com.example.a0112523013_fachrireyhan_p1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.a0112523013_fachrireyhan_p1.state.StudentEvent
import com.example.a0112523013_fachrireyhan_p1.state.StudentFormUiState

/** Tidak ada rememberSaveable, collectAsState, atau pemanggilan ViewModel dalam form. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFormScreen(
    state: StudentFormUiState,
    onEvent: (StudentEvent) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        containerColor = ScreenBackground,
        topBar = {
            TopAppBar(
                title = { Text(if (state.isEditing) "Edit Profil" else "Tambah Mahasiswa") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.semantics {
                        contentDescription = "Kembali"
                    }) { Text("‹", style = MaterialTheme.typography.headlineMedium) }
                }
            )
        }
    ) { padding ->
        if (!state.existingStudentFound) {
            Column(Modifier.padding(padding).padding(16.dp)) {
                Text("Mahasiswa tidak ditemukan")
                OutlinedButton(onClick = onBack) { Text("Kembali") }
            }
        } else {
            Column(
                Modifier.padding(padding).fillMaxSize().imePadding()
                    .verticalScroll(rememberScrollState())
            ) {
                ProfileHeader(
                    name = state.name.ifBlank { "Nama lengkap" },
                    major = "Semester ${state.semester} - Universitas Al Azhar Indonesia",
                    showFachriPhoto = state.editingId == 1
                )
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SectionCard(title = "Data Pribadi") {
                        ProfileTextField(
                            value = state.name,
                            onValueChange = { onEvent(StudentEvent.NameChanged(it)) },
                            label = "Nama lengkap", error = state.nameError
                        )
                        ProfileTextField(
                            value = state.nim,
                            onValueChange = { onEvent(StudentEvent.NimChanged(it)) },
                            label = "NIM", error = state.nimError,
                            keyboardType = KeyboardType.Number
                        )
                        ProfileTextField(
                            value = state.email,
                            onValueChange = { onEvent(StudentEvent.EmailChanged(it)) },
                            label = "Email", error = state.emailError,
                            keyboardType = KeyboardType.Email
                        )
                    }
                    SectionCard(title = "Pengaturan") {
                        CounterRow(
                            label = "Semester", value = state.semester, min = 1, max = 14,
                            onDecrement = { onEvent(StudentEvent.SemesterChanged(-1)) },
                            onIncrement = { onEvent(StudentEvent.SemesterChanged(1)) }
                        )
                        HorizontalDivider()
                        SwitchRow(
                            label = "Tampilkan Email", checked = state.showEmail,
                            onCheckedChange = { onEvent(StudentEvent.ShowEmailChanged(it)) }
                        )
                        HorizontalDivider()
                        CheckboxRow(
                            label = "Konfirmasi", checked = state.confirmed,
                            onCheckedChange = { onEvent(StudentEvent.ConfirmationChanged(it)) }
                        )
                    }
                    Button(onClick = onSave, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                        Text(if (state.isEditing) "Simpan Perubahan" else "Simpan Mahasiswa")
                    }
                    OutlinedButton(
                        onClick = { onEvent(StudentEvent.ResetClicked) },
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) { Text("Reset") }
                    SummaryCard(
                        name = state.name, nim = state.nim, email = state.email,
                        semester = state.semester, showEmail = state.showEmail,
                        isValid = state.isValid
                    )
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}
