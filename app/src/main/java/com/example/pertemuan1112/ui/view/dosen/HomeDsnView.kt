package com.example.pertemuan1112.ui.view.dosen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan1112.data.entity.Dosen
import com.example.pertemuan1112.ui.customwidget.CustomTopAppBar
import com.example.pertemuan1112.ui.viewmodel.HomeDsnViewModel
import com.example.pertemuan1112.ui.viewmodel.HomeUiStateDosen
import com.example.pertemuan1112.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeDsnView(
    viewModel: HomeDsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDsn: () -> Unit = { },
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold (

        topBar = {
            CustomTopAppBar(
                judul = "Daftar Dosen",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDsn,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Dosen",
                )
            }
        }
    ) { innerPadding ->
        val homeUiStateDosen by viewModel.homeUiStateDosen.collectAsState()

        BodyHomeDsnView(
            homeUiStateDosen = homeUiStateDosen,
            onClick = {},
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeDsnView(
    homeUiStateDosen: HomeUiStateDosen,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        homeUiStateDosen.isLoading -> {
            Box (
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        homeUiStateDosen.isError -> {
            LaunchedEffect(homeUiStateDosen.errorMessage) {
                homeUiStateDosen.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }

        homeUiStateDosen.listDsn.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data dosem.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListDosen(
                listDsn = homeUiStateDosen.listDsn,
                onClick = {
                    onClick(it)
                    println(it)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListDosen(
    listDsn: List<Dosen>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn (
        modifier = modifier
    ){
        items(
            items = listDsn,
            itemContent = { dsn ->
                CardDsn(
                    dsn = dsn,
                    onClick = {onClick(dsn.nidn)}
                )
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDsn(
    dsn: Dosen,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp), // Rounded corners for card
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Increased padding for better spacing
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "",
                    tint = Color(0xFF555E77)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = dsn.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF4B5D8D)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "",
                    tint = Color(0xFF555E77)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = dsn.nidn,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF4B5D8D)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "",
                    tint = Color(0xFF555E77)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = dsn.jenisKelamin,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4B5D8D)
                )
            }
        }
    }
}
