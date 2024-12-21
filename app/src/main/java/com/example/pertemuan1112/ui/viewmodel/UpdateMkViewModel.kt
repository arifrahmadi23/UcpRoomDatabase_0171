package com.example.pertemuan1112.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan1112.data.entity.MataKuliah
import com.example.pertemuan1112.repository.RepositoryMk
import com.example.pertemuan1112.ui.navigation.DestinasiUpdateMk
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMkViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMk: RepositoryMk
): ViewModel(){

    var updateUIStateMk by mutableStateOf(MkUiState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMk.KODE])

    init {
        viewModelScope.launch {
            updateUIStateMk = repositoryMk.getMk(_kode)
                .filterNotNull()
                .first()
                .toUiStateMk()
        }
    }

    fun updateState(mataKuliahEvent: MataKuliahEvent) {
        updateUIStateMk = updateUIStateMk.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }

    fun validateFields(): Boolean{
        val event = updateUIStateMk.mataKuliahEvent
        val errorState = FormErrorStateMk(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )

        updateUIStateMk = updateUIStateMk.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateUIStateMk.mataKuliahEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMk.updateMk(currentEvent.toMataKuliahEntity())
                    updateUIStateMk = updateUIStateMk.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorStateMk()
                    )
                    println("snackbarMessage diatur : ${updateUIStateMk.snackBarMessage}")
                } catch (e: Exception){
                    updateUIStateMk = updateUIStateMk.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else{
            updateUIStateMk = updateUIStateMk.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUIStateMk = updateUIStateMk.copy(snackBarMessage = null)
    }
}

fun MataKuliah.toUiStateMk(): MkUiState = MkUiState(
    mataKuliahEvent = this.toDetailUiEvent(),
)
