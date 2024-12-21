package com.example.pertemuan1112.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.pertemuan1112.KuliahApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            InsertDsnViewModel(
                KuliahApp().containerApp.RepositoryDsn
            )
        }
        initializer {
            HomeDsnViewModel(
                KuliahApp().containerApp.RepositoryDsn
            )
        }
    }
}
