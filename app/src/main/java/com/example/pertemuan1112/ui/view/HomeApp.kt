package com.example.pertemuan1112.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pertemuan1112.R
import com.example.pertemuan1112.ui.navigation.AlamatNavigasi

object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}

@Composable
fun HomeScreen(
    onClickDsn: () -> Unit = {},
    onClickMk: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.umy),
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.2f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header Section
                HeaderSection()

                // Buttons Section
                ButtonsSection(onClickDsn = onClickDsn, onClickMk = onClickMk)

                // Footer Section
                Text(
                    text = "Thank you for using HomeApp",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 32.dp) // Memberi jarak antara logo dan atas perangkat
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(250.dp) // Ukuran logo diperbesar
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(1000.dp) // Ukuran gambar logo diperbesar
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Daftar Data :",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun ButtonsSection(onClickDsn: () -> Unit, onClickMk: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CustomCard(
            title = "Daftar Dosen",
            description = "Nama, NIDN",
            buttonColor = MaterialTheme.colorScheme.primary,
            onClick = onClickDsn
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomCard(
            title = "Daftar Mata Kuliah",
            description = "Nama, Dosen Pengampu",
            buttonColor = MaterialTheme.colorScheme.secondary,
            onClick = onClickMk
        )
    }
}

@Composable
fun CustomCard(
    title: String,
    description: String,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Surface(
                modifier = Modifier
                    .size(80.dp) // Mengubah ukuran untuk teks "Masuk"
                    .clickable { onClick() }, // Menambahkan klik pada button
                shape = CircleShape,
                color = buttonColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Masuk", // Tulisan pada button
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimary // Kontras warna teks
                    )
                }
            }
        }
    }
}
