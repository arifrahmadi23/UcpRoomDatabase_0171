package com.example.pertemuan1112.repository

import com.example.pertemuan1112.data.dao.DosenDao
import com.example.pertemuan1112.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDsn(
    private val dosenDao: DosenDao
) :RepositoryDsn{
    override suspend fun insertDsn(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    override fun getAllDsn(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

}