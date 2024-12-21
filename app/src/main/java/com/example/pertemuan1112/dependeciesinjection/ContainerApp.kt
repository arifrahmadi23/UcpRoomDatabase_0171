package com.example.pertemuan1112.dependeciesinjection

import android.content.Context
import com.example.pertemuan1112.data.database.Kuliah
import com.example.pertemuan1112.repository.LocalRepositoryDsn
import com.example.pertemuan1112.repository.LocalRepositoryMk
import com.example.pertemuan1112.repository.RepositoryDsn
import com.example.pertemuan1112.repository.RepositoryMk

interface InterfaceContainerApp {
    val RepositoryDsn:RepositoryDsn
    val RepositoryMk:RepositoryMk
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val RepositoryDsn: RepositoryDsn by lazy {
        LocalRepositoryDsn(Kuliah.getDatabase(context).dosenDao())
    }
    override val RepositoryMk: RepositoryMk by lazy {
        LocalRepositoryMk(Kuliah.getDatabase(context).mataKuliahDao())
    }
}