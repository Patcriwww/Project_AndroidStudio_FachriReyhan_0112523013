package com.example.a0112523013_fachrireyhan_p1.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.a0112523013_fachrireyhan_p1.R

val ScreenBackground = Color(0xFFF4F6F9)

/**
 */
@Composable
fun StudentAvatar(
    name: String,
    showFachriPhoto: Boolean,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(size).clip(CircleShape).background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (showFachriPhoto) {
            Image(
                painter = painterResource(id = R.drawable.foto_profil),
                contentDescription = "Foto profil Fachri Reyhan",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Text(
                text = name.trim().firstOrNull()?.uppercase() ?: "?",
                style = if (size >= 80.dp) MaterialTheme.typography.headlineLarge
                        else MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    major: String,
    modifier: Modifier = Modifier,
    showFachriPhoto: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 28.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StudentAvatar(name = name, showFachriPhoto = showFachriPhoto, size = 96.dp)
        Spacer(Modifier.height(12.dp))
        Text(name, style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.White)
        Text(major, color = Color.White.copy(alpha = .88f), textAlign = TextAlign.Center)
    }
}

@Composable
fun SectionCard(title: String, modifier: Modifier = Modifier,
                content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            content()
        }
    }
}

@Composable
fun ProfileTextField(value: String, onValueChange: (String) -> Unit,
                     label: String, error: String?, modifier: Modifier = Modifier,
                     keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label) },
        isError = error != null,
        supportingText = error?.let { message -> { Text(message) } },
        singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun CounterRow(label: String, value: Int, min: Int, max: Int,
               onDecrement: () -> Unit, onIncrement: () -> Unit,
               modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Row(verticalAlignment = Alignment.CenterVertically) {
            FilledTonalIconButton(onClick = onDecrement, enabled = value > min,
                modifier = Modifier.size(36.dp)) { Text("-", fontWeight = FontWeight.Bold) }
            Text(value.toString(), style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center, modifier = Modifier.width(44.dp))
            FilledTonalIconButton(onClick = onIncrement, enabled = value < max,
                modifier = Modifier.size(36.dp)) { Text("+", fontWeight = FontWeight.Bold) }
        }
    }
}

@Composable
fun SwitchRow(label: String, checked: Boolean,
              onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().heightIn(min = 48.dp)
        .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = null)
    }
}

@Composable
fun CheckboxRow(label: String, checked: Boolean,
                onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth().heightIn(min = 48.dp)
        .toggleable(value = checked, role = Role.Checkbox, onValueChange = onCheckedChange),
        verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = null)
        Spacer(Modifier.width(12.dp))
        Text(label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.Gray)
        Spacer(Modifier.width(16.dp))
        Text(value, fontWeight = FontWeight.Medium, textAlign = TextAlign.End)
    }
}

@Composable
fun SummaryCard(name: String, nim: String, email: String,
                semester: Int, showEmail: Boolean, isValid: Boolean) {
    SectionCard(title = "Data Mahasiswa") {
        SummaryRow("Nama", name.ifBlank { "-" })
        SummaryRow("NIM", nim.ifBlank { "-" })
        SummaryRow("Semester", semester.toString())
        SummaryRow("Email", if (showEmail) email.ifBlank { "-" } else "(disembunyikan)")
        HorizontalDivider()
        Text(if (isValid) "Status: data valid" else "Status: data belum valid",
            color = if (isValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold)
    }
}
