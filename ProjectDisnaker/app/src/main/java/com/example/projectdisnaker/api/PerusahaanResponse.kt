package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

@Parcelize
data class PerusahaanResponse(

	@field:SerializedName("perusahaan")
	val perusahaan: Perusahaan? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class Perusahaan(

	@field:SerializedName("updated_at")
	val updatedAt: Timestamp? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("perusahaan_id")
	val perusahaanId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: Timestamp? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Timestamp? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
) : Parcelable
