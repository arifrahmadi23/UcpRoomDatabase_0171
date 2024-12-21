package com.example.pertemuan1112.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.pertemuan1112.data.entity.MataKuliah
import com.example.pertemuan1112.repository.RepositoryMk

class DetailMkViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMk: RepositoryMk,
) : ViewModel(){
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiDetail.kode])
}




data class DetailUiStateMk(
    val detailUiEventMk: MataKuliahEvent = MataKuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEventMk == MataKuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEventMk != MataKuliahEvent()
}

fun MataKuliah.toDetailUiEvent(): MataKuliahEvent {
    return MataKuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}
