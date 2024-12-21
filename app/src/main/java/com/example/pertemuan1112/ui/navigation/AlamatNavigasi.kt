package com.example.pertemuan1112.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHomeDosen : AlamatNavigasi{
    override val route = "home_dsn"
}