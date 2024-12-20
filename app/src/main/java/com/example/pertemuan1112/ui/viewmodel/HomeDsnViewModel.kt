package com.example.pertemuan1112.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan1112.data.entity.Dosen
import com.example.pertemuan1112.repository.RepositoryDsn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDsnViewModel(
    private val repositoryDsn: RepositoryDsn
) : ViewModel(){
    val homeUiStateDosen: StateFlow<HomeUiStateDosen> = repositoryDsn.getAllDsn()
        .filterNotNull()
        .map {
            HomeUiStateDosen(
                listDsn = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiStateDosen(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiStateDosen(
                isLoading = false,
                isError = true,
                errorMessage = it.message?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateDosen(
                isLoading = true,
            )
        )
}

data class HomeUiStateDosen(
    val listDsn: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String =""
)