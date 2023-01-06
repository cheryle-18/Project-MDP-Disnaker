package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PerusahaanResponse(

	@field:SerializedName("perusahaan")
	val perusahaan: List<PerusahaanItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class PerusahaanItem(

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("perusahaan_id")
	val perusahaanId: Int? = null,

) : Parcelable
