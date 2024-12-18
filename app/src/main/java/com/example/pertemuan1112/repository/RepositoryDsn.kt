package com.example.pertemuan1112.repository

import com.example.pertemuan1112.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDsn {
    suspend fun insertDsn(dosen: Dosen)

    fun getAllDsn(): Flow<List<Dosen>>
}