package com.example.a0112523013_fachrireyhan_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private val NavyPrimary = Color(0xFF2B3A4F)
private val ScreenBackground = Color(0xFFF4F6F9)

// Main Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(primary = NavyPrimary)
            ) {
                EditProfileScreen()
            }
        }
    }
}


// Komponen dari Project sebelumnya = ProfileHeader (Box + Column)
@Composable
fun ProfileHeader(
    name: String,
    major: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .statusBarsPadding()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.foto_profil),
                    contentDescription = "Foto profil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = major,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )
        }
    }
}


// 1. Logic Validasi
//    Mengembalikan pesan error, atau null jika valid.

private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

fun validateName(name: String): String? = when {
    name.isBlank() -> "Nama wajib diisi"
    name.trim().length < 3 -> "Nama minimal 3 karakter"
    else -> null
}

fun validateNim(nim: String): String? = when {
    nim.isBlank() -> "NIM wajib diisi"
    !nim.all { it.isDigit() } -> "NIM hanya boleh angka"
    nim.length != 10 -> "NIM harus 10 digit (${nim.length}/10)"
    else -> null
}

fun validateEmail(email: String): String? = when {
    email.isBlank() -> "Email wajib diisi"
    !EMAIL_REGEX.matches(email.trim()) -> "Format email tidak valid"
    else -> null
}

// 2. Screen Stateful
//    Semua state disimpan di sini
@Composable
fun EditProfileScreen() {
    // state input teks
    var name by rememberSaveable { mutableStateOf("") }
    var nim by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    // state counter, switch, checkbox
    var semester by rememberSaveable { mutableIntStateOf(1) }
    var showEmail by rememberSaveable { mutableStateOf(true) }
    var confirmed by rememberSaveable { mutableStateOf(false) }

    // error baru dipaksa tampil setelah user klik Simpan
    var submitted by rememberSaveable { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Dihitung ulang tiap recomposition
    val nameError = validateName(name).takeIf { name.isNotEmpty() || submitted }
    val nimError = validateNim(nim).takeIf { nim.isNotEmpty() || submitted }
    val emailError = validateEmail(email).takeIf { email.isNotEmpty() || submitted }

    val isFormValid = validateName(name) == null &&
            validateNim(nim) == null &&
            validateEmail(email) == null

    Scaffold(
        containerColor = ScreenBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(bottom = innerPadding.calculateBottomPadding())
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {

            ProfileHeader(
                name = name.ifBlank { "Nama lengkap" },
                major = "Semester $semester - Universitas Al Azhar Indonesia"
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Data Pribadi
                SectionCard(title = "Data Pribadi") {
                    ProfileTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Nama lengkap",
                        error = nameError
                    )
                    ProfileTextField(
                        value = nim,
                        onValueChange = { if (it.length <= 10) nim = it },
                        label = "NIM",
                        error = nimError,
                        keyboardType = KeyboardType.Number
                    )
                    ProfileTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        error = emailError,
                        keyboardType = KeyboardType.Email
                    )
                }

                // Pengaturan
                SectionCard(title = "Pengaturan") {
                    CounterRow(
                        label = "Semester",
                        value = semester,
                        min = 1,
                        max = 14,
                        onDecrement = { semester-- },
                        onIncrement = { semester++ }
                    )
                    HorizontalDivider()
                    SwitchRow(
                        label = "Tampilkan Email",
                        checked = showEmail,
                        onCheckedChange = { showEmail = it }
                    )
                    HorizontalDivider()
                    CheckboxRow(
                        label = "Konfirmasi",
                        checked = confirmed,
                        onCheckedChange = { confirmed = it }
                    )
                }

                Button(
                    onClick = {
                        submitted = true
                        val message = when {
                            !isFormValid -> "Periksa kembali data Anda"
                            !confirmed -> "Tolong konfirmasi terlebih dahulu"
                            else -> "Profil ${name.trim()} Berhasil Disimpan"
                        }
                        scope.launch { snackbarHostState.showSnackbar(message) }

                        // Jika berhasil disimpan, form kembali ke kondisi awal
                        if (isFormValid && confirmed) {
                            name = ""
                            nim = ""
                            email = ""
                            semester = 1
                            showEmail = true
                            confirmed = false
                            submitted = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Simpan")
                }

                // Tombol Reset
                OutlinedButton(
                    onClick = {
                        name = ""
                        nim = ""
                        email = ""
                        semester = 1
                        showEmail = true
                        confirmed = false
                        submitted = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Reset")
                }

                SummaryCard(
                    name = name,
                    nim = nim,
                    email = email,
                    semester = semester,
                    showEmail = showEmail,
                    isValid = isFormValid
                )
            }
        }
    }
}

//
// Komponen Stateless
@Composable
fun SectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            content()
        }
    }
}

@Composable
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = error?.let { message -> { Text(message) } },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun CounterRow(
    label: String,
    value: Int,
    min: Int,
    max: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            FilledTonalIconButton(
                onClick = onDecrement,
                enabled = value > min,
                modifier = Modifier.size(36.dp)
            ) { Text("-", fontWeight = FontWeight.Bold) }
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(44.dp)
            )
            FilledTonalIconButton(
                onClick = onIncrement,
                enabled = value < max,
                modifier = Modifier.size(36.dp)
            ) { Text("+", fontWeight = FontWeight.Bold) }
        }
    }
}

@Composable
fun SwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(checked = checked, onCheckedChange = null)
    }
}

@Composable
fun CheckboxRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .toggleable(value = checked, role = Role.Checkbox, onValueChange = onCheckedChange),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = checked, onCheckedChange = null)
        Spacer(Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray)
        Spacer(Modifier.width(16.dp))
        Text(text = value, fontWeight = FontWeight.Medium, textAlign = TextAlign.End)
    }
}

@Composable
fun SummaryCard(
    name: String,
    nim: String,
    email: String,
    semester: Int,
    showEmail: Boolean,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    SectionCard(title = "Data Mahasiswa", modifier = modifier) {
        SummaryRow("Nama", name.ifBlank { "-" })
        SummaryRow("NIM", nim.ifBlank { "-" })
        SummaryRow("Semester", semester.toString())
        SummaryRow(
            "Email",
            if (showEmail) email.ifBlank { "-" } else "(disembunyikan)"
        )
        HorizontalDivider()
        Text(
            text = if (isValid) "Status: data valid" else "Status: data belum valid",
            color = if (isValid) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold
        )
    }
}

// 4. PREVIEW
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfileScreenPreview() {
    MaterialTheme(
        colorScheme = lightColorScheme(primary = NavyPrimary)
    ) {
        EditProfileScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun CounterRowPreview() {
    MaterialTheme(colorScheme = lightColorScheme(primary = NavyPrimary)) {
        CounterRow(
            label = "Semester", value = 3, min = 1, max = 14,
            onDecrement = {}, onIncrement = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}