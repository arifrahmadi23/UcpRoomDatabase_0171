package com.example.pertemuan1112.ui.viewmodel

import com.example.pertemuan1112.data.entity.MataKuliah

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
