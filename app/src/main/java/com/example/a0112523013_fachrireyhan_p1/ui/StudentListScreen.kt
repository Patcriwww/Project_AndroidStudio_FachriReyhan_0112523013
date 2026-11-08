package com.example.a0112523013_fachrireyhan_p1.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a0112523013_fachrireyhan_p1.data.Student
import com.example.a0112523013_fachrireyhan_p1.state.StudentListUiState

/** Stateless UI: hanya menerima state dan callback, tanpa ViewModel/NavController. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(
    state: StudentListUiState,
    onSearchChanged: (String) -> Unit,
    onStudentClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { CenterAlignedTopAppBar(
            title = { Text("Daftar Mahasiswa") },
            actions = { TextButton(onClick = onSettingsClick) { Text("Pengaturan") } }
        ) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                content = { Text("+ Tambah Profil") }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            Text(
                "Student Profile Manager",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 16.dp)
            )
            Text(
                "${state.totalCount} profil mahasiswa • Tersimpan di Room",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
            OutlinedTextField(
                value = state.query,
                onValueChange = onSearchChanged,
                label = { Text("Cari nama atau NIM") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            )
            if (state.visibleStudents.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Mahasiswa tidak ditemukan", style = MaterialTheme.typography.titleMedium)
                    Text("Coba kata kunci lain atau hapus pencarian.")
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(onClick = { onSearchChanged("") }) {
                        Text("Hapus pencarian")
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 110.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.visibleStudents, key = { it.id }) { student ->
                        StudentListItem(student, onClick = { onStudentClick(student.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun StudentListItem(student: Student, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            StudentAvatar(name = student.name, showFachriPhoto = student.id == 1, size = 52.dp)
            Column(Modifier.weight(1f)) {
                Text(student.name, style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold)
                Text("NIM: ${student.nim}", style = MaterialTheme.typography.bodyMedium)
                Text("Semester ${student.semester}", style = MaterialTheme.typography.bodySmall)
            }
            Text("›", style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary)
        }
    }
}
