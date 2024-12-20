package com.example.pertemuan1112.ui.viewmodel

import android.os.Message
import com.example.pertemuan1112.data.entity.Dosen

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
