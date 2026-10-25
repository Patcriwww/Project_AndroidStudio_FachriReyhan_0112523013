package com.example.a0112523013_fachrireyhan_p1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.a0112523013_fachrireyhan_p1.validation.*
import com.example.a0112523013_fachrireyhan_p1.viewmodel.StudentViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFormScreen(
    model: StudentViewModel,
    editingId: Int?,
    onBack: () -> Unit,
    onSaved: (String) -> Unit
) {
    val students by model.students.collectAsState()
    val original = students.firstOrNull { it.id == editingId }
    val isEditing = editingId != null
    // rememberSaveable menjaga nilai input ketika orientasi layar berubah.
    var name by rememberSaveable(editingId) { mutableStateOf(original?.name.orEmpty()) }
    var nim by rememberSaveable(editingId) { mutableStateOf(original?.nim.orEmpty()) }
    var email by rememberSaveable(editingId) { mutableStateOf(original?.email.orEmpty()) }
    var semester by rememberSaveable(editingId) { mutableIntStateOf(original?.semester ?: 1) }
    var showEmail by rememberSaveable(editingId) { mutableStateOf(original?.showEmail ?: true) }
    var confirmed by rememberSaveable(editingId) { mutableStateOf(false) }
    var submitted by rememberSaveable(editingId) { mutableStateOf(false) }

    val nameError = validateName(name).takeIf { name.isNotEmpty() || submitted }
    val uniqueNimError = validateUniqueNim(nim, students.filter { it.id != editingId }.map { it.nim })
    val nimError = (validateNim(nim) ?: uniqueNimError).takeIf { nim.isNotEmpty() || submitted }
    val emailError = validateEmail(email).takeIf { email.isNotEmpty() || submitted }
    val isFormValid = validateName(name) == null && validateNim(nim) == null &&
        uniqueNimError == null && validateEmail(email) == null && validateSemester(semester) == null
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun resetForm() {
        name = original?.name.orEmpty()
        nim = original?.nim.orEmpty()
        email = original?.email.orEmpty()
        semester = original?.semester ?: 1
        showEmail = original?.showEmail ?: true
        confirmed = false
        submitted = false
    }

    Scaffold(
        containerColor = ScreenBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Profil" else "Tambah Mahasiswa") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.semantics {
                        contentDescription = "Kembali"
                    }) { Text("‹", style = MaterialTheme.typography.headlineMedium) }
                }
            )
        }
    ) { padding ->
        if (isEditing && original == null) {
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
                    name = name.ifBlank { "Nama lengkap" },
                    major = "Semester $semester - Universitas Al Azhar Indonesia",
                    showFachriPhoto = editingId == 1
                )
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    SectionCard(title = "Data Pribadi") {
                        ProfileTextField(value = name, onValueChange = { name = it },
                            label = "Nama lengkap", error = nameError)
                        ProfileTextField(value = nim,
                            onValueChange = { if (it.length <= 10) nim = it },
                            label = "NIM", error = nimError, keyboardType = KeyboardType.Number)
                        ProfileTextField(value = email, onValueChange = { email = it },
                            label = "Email", error = emailError, keyboardType = KeyboardType.Email)
                    }
                    SectionCard(title = "Pengaturan") {
                        CounterRow(label = "Semester", value = semester, min = 1, max = 14,
                            onDecrement = { semester-- }, onIncrement = { semester++ })
                        HorizontalDivider()
                        SwitchRow(label = "Tampilkan Email", checked = showEmail,
                            onCheckedChange = { showEmail = it })
                        HorizontalDivider()
                        CheckboxRow(label = "Konfirmasi", checked = confirmed,
                            onCheckedChange = { confirmed = it })
                    }
                    Button(
                        onClick = {
                            submitted = true
                            when {
                                !isFormValid -> scope.launch {
                                    snackbarHostState.showSnackbar("Periksa kembali data Anda")
                                }
                                !confirmed -> scope.launch {
                                    snackbarHostState.showSnackbar("Tolong konfirmasi terlebih dahulu")
                                }
                                else -> {
                                    val error = model.save(
                                        editingId = editingId, name = name, nim = nim,
                                        email = email, semester = semester, showEmail = showEmail
                                    )
                                    if (error == null) {
                                        val message = if (isEditing) {
                                            "Profil ${name.trim()} berhasil diperbarui"
                                        } else {
                                            "Mahasiswa ${name.trim()} berhasil ditambahkan"
                                        }
                                        onSaved(message)
                                    } else {
                                        scope.launch { snackbarHostState.showSnackbar(error) }
                                    }
                                }
                            }
                        }, modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) { Text(if (isEditing) "Simpan Perubahan" else "Simpan Mahasiswa") }
                    OutlinedButton(
                        onClick = {
                            resetForm()
                            scope.launch { snackbarHostState.showSnackbar("Form berhasil direset") }
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) { Text("Reset") }
                    SummaryCard(name = name, nim = nim, email = email,
                        semester = semester, showEmail = showEmail, isValid = isFormValid)
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}
