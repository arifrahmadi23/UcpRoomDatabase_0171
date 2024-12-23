package com.example.pertemuan1112.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan1112.data.entity.Dosen
import com.example.pertemuan1112.data.entity.MataKuliah
import com.example.pertemuan1112.repository.RepositoryDsn
import com.example.pertemuan1112.repository.RepositoryMk
import kotlinx.coroutines.launch

class InsertMkViewModel(private val repositoryMk: RepositoryMk, private val repositoryDsn: RepositoryDsn
) : ViewModel(){
    var uiState by mutableStateOf(MkUiState())

    var mataKuliahList by mutableStateOf<List<MataKuliah>>(emptyList())
        private set

    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set

    private fun updateUiState() {
        uiState = uiState.copy(dosenList = dosenList)
    }

    init {
        viewModelScope.launch {
            repositoryMk.getAllMk().collect { mkList ->
                this@InsertMkViewModel.mataKuliahList = mkList
            }
        }
        viewModelScope.launch {
            repositoryDsn.getAllDsn().collect { dosenList ->
                this@InsertMkViewModel.dosenList = dosenList
                updateUiState()
            }
        }
    }




    fun updateState (mataKuliahEvent: MataKuliahEvent){
        uiState = uiState.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }
    fun validateFieldsMk(): Boolean {
        val event = uiState.mataKuliahEvent
        val errorState = FormErrorStateMk(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else  "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun saveData(){
        val currentEvent = uiState.mataKuliahEvent
        if (validateFieldsMk()) {
            viewModelScope.launch {
                try {
                    repositoryMk.insertMataKuliah(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(), // Reset input form
                        isEntryValid = FormErrorStateMk() // Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState =uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }

    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class MataKuliahEvent(
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)

fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

data class MkUiState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorStateMk = FormErrorStateMk(),
    val snackBarMessage: String? = null,
    val dosenList: List<Dosen> = emptyList()
)

data class FormErrorStateMk(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama == null && sks == null && semester == null &&
                jenis == null && dosenPengampu == null
    }
}