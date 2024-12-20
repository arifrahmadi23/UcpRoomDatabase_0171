package com.example.pertemuan1112.ui.viewmodel

import com.example.pertemuan1112.data.entity.Dosen

//Menyimpan Input dari Form
data class DosenEvent(
    val nidn: String ="",
    val nama: String ="",
    val jenisKelamin: String=""
)

// Menyimpan input form ke dalam entity
fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)
