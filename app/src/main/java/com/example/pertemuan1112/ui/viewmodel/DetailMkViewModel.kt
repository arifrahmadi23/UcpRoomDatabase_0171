package com.example.pertemuan1112.ui.viewmodel

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