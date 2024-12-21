package com.example.pertemuan1112.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHomeDosen : AlamatNavigasi{
    override val route = "home_dsn"
}

object DestinasiDetailMk : AlamatNavigasi{
    override val route = "detail_mk"
        const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMk : AlamatNavigasi{
    override val route = "update_mk"
    const val KODE = "kode"
    val routeWithArg = "$route/{$KODE}"
}