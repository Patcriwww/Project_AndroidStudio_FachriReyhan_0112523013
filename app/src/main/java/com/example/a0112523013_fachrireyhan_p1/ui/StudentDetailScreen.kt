package com.example.a0112523013_fachrireyhan_p1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.a0112523013_fachrireyhan_p1.state.StudentDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDetailScreen(
    state: StudentDetailUiState,
    studentId: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDelete: (Int) -> Unit
) {
    val student = state.student
    var confirmDelete by rememberSaveable { mutableStateOf(false) }
    if (confirmDelete && student != null) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("Hapus mahasiswa?") },
            text = { Text("Profil ${student.name} akan dihapus dari database lokal.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmDelete = false
                    onDelete(student.id)
                }) { Text("Hapus") }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = false }) { Text("Batal") }
            }
        )
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Detail Mahasiswa") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.semantics {
                        contentDescription = "Kembali"
                    }) { Text("‹", style = MaterialTheme.typography.headlineMedium) }
                }
            )
        }
    ) { padding ->
        if (student == null) {
            Column(
                Modifier.padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("Data mahasiswa dengan ID $studentId tidak ditemukan.")
                OutlinedButton(onClick = onBack) { Text("Kembali") }
            }
        } else {
            Column(
                modifier = Modifier.padding(padding).fillMaxSize()
                    .verticalScroll(rememberScrollState()).imePadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileHeader(
                    name = student.name,
                    major = "Semester ${student.semester} • Informatika",
                    showFachriPhoto = student.id == 1
                )
                Column(
                    Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SectionCard(title = "Informasi Mahasiswa") {
                        SummaryRow("ID Profil", "${student.id}")
                        SummaryRow("NIM", student.nim)
                        SummaryRow("Program Studi", "Informatika")
                        SummaryRow("Semester", "${student.semester}")
                        SummaryRow("Email", if (student.showEmail) student.email else "(disembunyikan)")
                    }
                    Button(onClick = onEdit, modifier = Modifier.fillMaxWidth().height(48.dp)) {
                        Text("Edit Profil")
                    }
                    OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                        Text("Kembali ke daftar")
                    }
                    OutlinedButton(onClick = { confirmDelete = true },
                        modifier = Modifier.fillMaxWidth()) { Text("Hapus Profil") }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
