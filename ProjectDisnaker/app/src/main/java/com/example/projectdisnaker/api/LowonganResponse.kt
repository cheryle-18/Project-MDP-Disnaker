package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

@Parcelize
data class LowonganResponse(

	@field:SerializedName("lowongan")
	val lowongan: List<LowonganItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class SyaratItem(

	@field:SerializedName("updated_at")
	val updatedAt: Timestamp? = null,

	@field:SerializedName("sl_id")
	val slId: Int? = null,

	@field:SerializedName("lowongan_id")
	val lowonganId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: Timestamp? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Timestamp? = null
) : Parcelable

@Parcelize
data class LowonganItem(

	@field:SerializedName("kategori_id")
	val kategoriId: Int? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Timestamp? = null,

	@field:SerializedName("perusahaan_id")
	val perusahaanId: Int? = null,

	@field:SerializedName("kuota")
	val kuota: Int? = null,

	@field:SerializedName("lowongan_id")
	val lowonganId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: Timestamp? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Timestamp? = null,

	@field:SerializedName("syarat")
	val syarat: List<SyaratItem?>? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable
