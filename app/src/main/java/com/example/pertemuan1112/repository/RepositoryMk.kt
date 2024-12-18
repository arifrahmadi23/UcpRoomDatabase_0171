package com.example.pertemuan1112.repository

import com.example.pertemuan1112.data.entity.Dosen
import com.example.pertemuan1112.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMk {
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    fun getAllMk(): Flow<List<MataKuliah>>

    fun getMk(kode :String): Flow<MataKuliah>

    suspend fun deleteMk(mataKuliah: MataKuliah)

    suspend fun updateMk(mataKuliah: MataKuliah)
}