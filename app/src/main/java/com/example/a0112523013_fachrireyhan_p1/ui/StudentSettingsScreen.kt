package com.example.a0112523013_fachrireyhan_p1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.a0112523013_fachrireyhan_p1.data.preferences.StudentSortOrder
import com.example.a0112523013_fachrireyhan_p1.data.preferences.UserSettings

/** UI tidak membaca DataStore langsung; hanya menerima state dan callback. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentSettingsScreen(
    state: UserSettings,
    onDarkModeChange: (Boolean) -> Unit,
    onSortOrderChange: (StudentSortOrder) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.semantics {
                        contentDescription = "Kembali"
                    }) { Text("‹", style = MaterialTheme.typography.headlineMedium) }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card {
                Row(Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(Modifier.weight(1f)) {
                        Text("Dark Mode", style = MaterialTheme.typography.titleMedium)
                        Text("Tema disimpan secara lokal di DataStore")
                    }
                    Switch(checked = state.darkMode, onCheckedChange = onDarkModeChange)
                }
            }
            Card {
                Column(Modifier.fillMaxWidth().padding(16.dp)) {
                    Text("Urutan Daftar", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    StudentSortOrder.entries.forEach { order ->
                        Row(
                            modifier = Modifier.fillMaxWidth().selectable(
                                selected = state.sortOrder == order,
                                onClick = { onSortOrderChange(order) }
                            ).padding(vertical = 8.dp),
                        ) {
                            RadioButton(
                                selected = state.sortOrder == order,
                                onClick = { onSortOrderChange(order) }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(order.label)
                        }
                    }
                }
            }
            Text("Pengaturan akan tetap ada setelah aplikasi ditutup lalu dibuka kembali.",
                style = MaterialTheme.typography.bodySmall)
        }
    }
}
