package com.example.pertemuan1112.ui.viewmodel

import com.example.pertemuan1112.data.entity.MataKuliah

fun MataKuliah.toUiStateMk(): MkUiState = MkUiState(
    mataKuliahEvent = this.toDetailUiEvent(),
)
