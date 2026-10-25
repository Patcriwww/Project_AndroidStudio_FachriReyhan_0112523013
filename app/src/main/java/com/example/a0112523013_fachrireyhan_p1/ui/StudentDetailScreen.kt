package com.example.a0112523013_fachrireyhan_p1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.a0112523013_fachrireyhan_p1.viewmodel.StudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDetailScreen(
    model: StudentViewModel,
    studentId: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    // Mengambil item berdasarkan ID dari route, bukan berdasarkan posisi list.
    val students by model.students.collectAsState()
    val student = students.firstOrNull { it.id == studentId }
    Scaffold(
        containerColor = ScreenBackground,
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
            Column(Modifier.padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)) {
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
                Column(Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {
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
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
