package com.example.projectdisnaker.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PendaftaranResponse(

    @field:SerializedName("pelatihan")
    val pelatihan: List<PendaftaranPelatihanItem>? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable

@Parcelize
data class PendaftaranPelatihanItem(

    @field:SerializedName("pp_id")
    val pp_id: Int? = null,

    @field:SerializedName("pelatihan_nama")
    val pelatihan_nama: String? = null,

    @field:SerializedName("kategori")
    val kategori: String? = null,

    @field:SerializedName("status_pendaftaran")
    val status_pendaftaran: Int? = null,

    @field:SerializedName("status_kelulusan")
    val status_kelulusan: Int? = null,
) : Parcelable

