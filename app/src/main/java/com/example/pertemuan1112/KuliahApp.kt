package com.example.pertemuan1112

import android.app.Application
import com.example.pertemuan1112.dependeciesinjection.ContainerApp

class KuliahApp : Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this)
    }
}