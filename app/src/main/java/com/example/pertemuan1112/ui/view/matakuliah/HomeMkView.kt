package com.example.pertemuan1112.ui.view.matakuliah

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
import com.example.pertemuan1112.data.entity.MataKuliah
import com.example.pertemuan1112.ui.customwidget.CustomTopAppBar
import com.example.pertemuan1112.ui.viewmodel.HomeMkViewModel
import com.example.pertemuan1112.ui.viewmodel.HomeUiStateMataKuliah
import com.example.pertemuan1112.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeMkView(
    viewModel: HomeMkViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit,
    onAddMk: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold (

        topBar = {
            CustomTopAppBar(
                judul = "Daftar MataKuliah",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMk,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah MataKuliah",
                )
            }
        }
    ) { innerPadding ->
        val homeUiStateMk by viewModel.homeUiStateMataKuliah.collectAsState()

        BodyHomeMkView(
            homeUiStateMataKuliah = homeUiStateMk,
            onClick = {
                onDetailClick(it)
            },
            modifier = modifier.padding(innerPadding)
        )
    }
}



@Composable
fun BodyHomeMkView(
    homeUiStateMataKuliah: HomeUiStateMataKuliah,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        homeUiStateMataKuliah.isLoading -> {
            Box (
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        homeUiStateMataKuliah.isError -> {
            LaunchedEffect(homeUiStateMataKuliah.errorMessage) {
                homeUiStateMataKuliah.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }

        homeUiStateMataKuliah.listMk.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data mahasiswa.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListMataKuliah(
                listMk = homeUiStateMataKuliah.listMk,
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
fun ListMataKuliah(
    listMk: List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn (
        modifier = modifier
    ){
        items(
            items = listMk,
            itemContent = { mk ->
                CardMk(
                    mk = mk,
                    onClick = {onClick(mk.kode)}
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMk(
    mk: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp), // Rounded corners for card
        colors = CardDefaults.cardColors(containerColor = Color.White) // White background for card
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
                    tint = Color(0xFF4B5D8D) // Icon color updated
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = mk.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF4B5D8D) // Text color updated
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
                    tint = Color(0xFF4B5D8D) // Icon color updated
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = mk.kode,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF4B5D8D) // Text color updated
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
                    tint = Color(0xFF4B5D8D) // Icon color updated
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = mk.sks,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4B5D8D) // Text color updated
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
                    tint = Color(0xFF4B5D8D) // Icon color updated
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = mk.jenis,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4B5D8D) // Text color updated
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
                    tint = Color(0xFF4B5D8D) // Icon color updated
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = mk.dosenPengampu,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4B5D8D) // Text color updated
                )
            }
        }
    }
}
