package com.example.a0112523013_fachrireyhan_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

// LANGKAH 0: MainActivity (titik masuk aplikasi)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme (
                colorScheme = lightColorScheme(
                    primary = Color(0xFF2B3A4F)
                )
                ) {
                ProfileScreen()
            }
        }
    }
}

// Custom composable #1 -> ProfileHeader
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
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Box untuk avatar bulat
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ){
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
                color = Color.White
            )
            Text(
                text = major,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

// ============================================================
// LANGKAH 3: Custom composable #2 -> InfoItem (reusable)
// Memakai Row (ikon di kiri, teks di kanan)
// Dipakai berkali-kali dengan data berbeda
// ============================================================
@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

// ============================================================
// LANGKAH 4: ProfileScreen (1 screen Compose)
// Menyusun ProfileHeader + Card berisi InfoItem + Button
// ============================================================
@Composable
fun ProfileScreen() {
    // State sederhana: tombol berubah teks saat diklik
    var terhubung by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        ProfileHeader(
            name = "Fachri Reyhan",              // TODO: ganti dengan namamu
            major = "Informatika - Universitas Al Azhar Indonesia"
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Informasi Kampus",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                // InfoItem dipakai ulang 4x -> inilah gunanya composable reusable
                InfoItem(Icons.Default.Home, "Kampus", "Universitas Al Azhar Indonesia")
                InfoItem(Icons.Default.Person, "NIM", "0112523013")   // TODO: ganti
                InfoItem(Icons.Default.Email, "Email", "fachrireyhan@student.uai.ac.id") // TODO
                InfoItem(Icons.Default.Place, "Lokasi", "Jakarta Selatan")
            }
        }

        Button(
            onClick = { terhubung = !terhubung },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(Icons.Default.Phone, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (terhubung) "Terhubung ✓" else "Hubungi Saya")
        }
    }
}

// ============================================================
// LANGKAH 5: @Preview (lihat tampilan tanpa menjalankan emulator)
// ============================================================
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}

