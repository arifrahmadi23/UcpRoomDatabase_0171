package com.example.pertemuan1112.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan1112.data.entity.MataKuliah
import com.example.pertemuan1112.repository.RepositoryMk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMkViewModel(
    private val repositoryMk: RepositoryMk
) : ViewModel() {
    val homeUiStateMataKuliah: StateFlow<HomeUiStateMataKuliah> = repositoryMk.getAllMk()
        .filterNotNull()
        .map {
            HomeUiStateMataKuliah(
                listMk = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiStateMataKuliah(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiStateMataKuliah(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateMataKuliah(
                isLoading = true,
            )
        )
}

data class HomeUiStateMataKuliah(
    val listMk: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError : Boolean = false,
    val errorMessage: String = ""
)