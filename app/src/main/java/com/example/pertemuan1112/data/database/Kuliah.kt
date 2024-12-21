package com.example.pertemuan1112.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pertemuan1112.data.dao.DosenDao
import com.example.pertemuan1112.data.dao.MataKuliahDao
import com.example.pertemuan1112.data.entity.Dosen
import com.example.pertemuan1112.data.entity.MataKuliah

@Database(entities = [Dosen::class,MataKuliah::class], version = 1, exportSchema = false)
abstract class Kuliah : RoomDatabase(){

    abstract fun dosenDao(): DosenDao
    abstract fun mataKuliahDao(): MataKuliahDao

    companion object{
        @Volatile
        private var Instance: Kuliah? = null

        fun getDatabase(context: Context): Kuliah{
            return (Instance?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    Kuliah::class.java,
                    "KrsDatabase"
                )
                    .build().also { Instance = it }
            })
        }
    }
}