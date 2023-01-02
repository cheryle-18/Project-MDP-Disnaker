package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RiwayatResponse(

	@field:SerializedName("riwayat")
	val riwayat: List<RiwayatItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class RiwayatItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("perusahaan")
	val perusahaan: String? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null
) : Parcelable
