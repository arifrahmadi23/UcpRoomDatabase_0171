package com.example.pertemuan1112.ui.viewmodel

import android.os.Message
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan1112.data.entity.Dosen
import com.example.pertemuan1112.repository.RepositoryDsn
import kotlinx.coroutines.launch

class InsertDsnViewModel(private val repositoryDsn: RepositoryDsn
) : ViewModel(){
    var uiState by mutableStateOf(DsnUiState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState (dosenEvent: DosenEvent){
        uiState = uiState.copy(
            dosenEvent = dosenEvent,
        )
    }

    // Validsai data input pengguna
    private fun validateFields(): Boolean{
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data ke repository
    fun saveData(){
        val currentEvent = uiState.dosenEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryDsn.insertDsn(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dosenEvent = DosenEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda"
            )
        }
    }
    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

//Menyimpan Input dari Form
data class DosenEvent(
    val nidn: String ="",
    val nama: String ="",
    val jenisKelamin: String=""
)

// Menyimpan input form ke dalam entity
fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)

data class DsnUiState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

// Memberikan Validasi apakah input form sudah benar atau belum
data class FormErrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null
){
    fun isValid(): Boolean{
        return nidn == null && nama == null && jenisKelamin == null
    }
}
