package com.example.pertemuan1112.ui.viewmodel

import com.example.pertemuan1112.data.entity.Dosen

data class HomeUiStateDosen(
    val listDsn: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String =""
)