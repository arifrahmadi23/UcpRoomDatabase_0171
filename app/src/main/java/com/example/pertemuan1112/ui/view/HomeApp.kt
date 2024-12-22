package com.example.pertemuan1112.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    // Using a Column layout with top padding and Center alignment for content
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to HomeApp",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // First button with some padding, background, and rounded corners
        Button(
            onClick = onClickDsn,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE),  // Purple color for the button
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(16.dp),
            shape = RoundedCornerShape(12.dp), // Rounded corners
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)  // Space between the buttons
        ) {
            Text(text = "Navigate to DSN")
        }

        // Second button with slightly different styling
        Button(
            onClick = onClickMk,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF03DAC5),  // Teal color for the button
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(16.dp),
            shape = RoundedCornerShape(12.dp), // Rounded corners
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Navigate to MK")
        }
    }
}
