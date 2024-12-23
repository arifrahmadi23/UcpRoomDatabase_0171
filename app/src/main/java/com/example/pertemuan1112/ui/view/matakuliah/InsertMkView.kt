package com.example.pertemuan1112.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan1112.data.entity.Dosen
import com.example.pertemuan1112.ui.customwidget.CustomTopAppBar
import com.example.pertemuan1112.ui.navigation.AlamatNavigasi
import com.example.pertemuan1112.ui.view.dosen.ListDosen
import com.example.pertemuan1112.ui.viewmodel.FormErrorStateMk
import com.example.pertemuan1112.ui.viewmodel.InsertMkViewModel
import com.example.pertemuan1112.ui.viewmodel.MataKuliahEvent
import com.example.pertemuan1112.ui.viewmodel.MkUiState
import com.example.pertemuan1112.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiInsertMk : AlamatNavigasi {
    override val route: String = "insert_mk"
}

@Composable
fun InsertMkView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertMkViewModel = viewModel(factory = PenyediaViewModel.Factory) // Instalasi View
){
    val uiState = viewModel.uiState // Ambil UI State dari ViewModel
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) // Tampilkan snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        topBar = {CustomTopAppBar(
            onBack = onBack,
            showBackButton = true,
            judul = "Tambah Matakuliah"
        )},
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Tempatkan snackbar di scaffold
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // isi Body
            InsertBodyMk (
                uiState = uiState,
                dosenList = uiState.dosenList,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent) // Update state di ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                        if (viewModel.validateFieldsMk()){
                            viewModel.saveData()
                            delay(500)
                            withContext(Dispatchers.Main){
                                onNavigate()
                            }
                        }
                    }
                }
            )
        }
    }
}




@Composable
fun InsertBodyMk(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MkUiState,
    onClick: () -> Unit,
    dosenList: List<Dosen>
){
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMataKuliah(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            dosenList = dosenList
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMataKuliah(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit,
    errorState: FormErrorStateMk = FormErrorStateMk(),
    modifier: Modifier = Modifier,
    dosenList: List<Dosen>

) {
    var chosenDropdown by remember { mutableStateOf(mataKuliahEvent.dosenPengampu) } // Default to the current value
    var expanded by remember { mutableStateOf(false) }
    val jenis = listOf("Wajib", "Pilihan")
    
    Column (
        modifier = modifier.fillMaxWidth()
    ){

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.nama,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama mata kuliah") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode, onValueChange = {
                onValueChange(mataKuliahEvent.copy(kode = it))
            },
            label = { Text("Kode") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Matakuliah") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.kode ?: "", color = Color.Red)


        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sks, onValueChange = {
                onValueChange(mataKuliahEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan SKS") },
        )
        Text(text = errorState.sks ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.semester, onValueChange = {
                onValueChange(mataKuliahEvent.copy(semester = it))
            },
            label = { Text("Semester") },
            isError = errorState.semester != null,
            placeholder = { Text("Masukkan Semester") },

            )
        Text(text = errorState.semester ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis")
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            jenis.forEach { jk ->
                Row (verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mataKuliahEvent.jenis == jk,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(jenis = jk))
                        },
                    )
                    Text(text = jk)
                }
            }
        }
        Text(text = errorState.jenis ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = chosenDropdown,
                onValueChange = { /* Cannot be changed manually */ },
                label = { Text("Pilih Dosen Pengampu") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand menu"
                    )
                },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = true,
                isError = errorState.dosenPengampu != null
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dosenList.forEach { dosen ->
                    DropdownMenuItem(
                        onClick = {
                            chosenDropdown = dosen.nama
                            expanded = false
                            onValueChange(mataKuliahEvent.copy(dosenPengampu = dosen.nama))
                        },
                        text = { Text(text = dosen.nama) },

                        )
                }
            }
        }
        Text(text = errorState.dosenPengampu ?: "", color = Color.Red)




    }
}

