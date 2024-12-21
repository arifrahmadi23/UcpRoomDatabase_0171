package com.example.pertemuan1112.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan1112.data.entity.MataKuliah
import com.example.pertemuan1112.repository.RepositoryMk
import com.example.pertemuan1112.ui.navigation.DestinasiDetailMk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMkViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMk: RepositoryMk,
) : ViewModel(){
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiDetailMk.KODE])

    val detailUiStateMk: StateFlow<DetailUiStateMk> = repositoryMk.getMk(_kode)
        .filterNotNull()
        .map {
            DetailUiStateMk(
                detailUiEventMk = it.toDetailUiEvent(),
                isLoading = false
            )
        }
        .onStart {
            emit(DetailUiStateMk(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiStateMk(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiStateMk(
                isLoading = true,
            ),
        )
    fun deleteMk(){
        detailUiStateMk.value.detailUiEventMk.toMataKuliahEntity().let {
            viewModelScope.launch {
                repositoryMk.deleteMk(it)
            }
        }
    }
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
