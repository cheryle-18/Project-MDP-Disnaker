package com.example.projectdisnaker.peserta

class Pelatihan(
    var id: String,
    var nama: String,
    var kuota: Int,
    var jmlhPeserta: Int,
    var durasi: Int,
    var syarat: Array<String>,
    var peluangKerja: Array<String>
) {
}