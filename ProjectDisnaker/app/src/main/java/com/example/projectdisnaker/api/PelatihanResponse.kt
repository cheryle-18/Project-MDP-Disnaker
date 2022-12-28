package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PelatihanResponse(

	@field:SerializedName("pelatihan")
	val pelatihan: List<PelatihanItem?>? = null
) : Parcelable

@Parcelize
data class PelatihanItem(

	@field:SerializedName("pelatihan_id")
	val pelatihanId: Int? = null,

	@field:SerializedName("kategori_id")
	val kategoriId: Int? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("pendidikan")
	val pendidikan: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("kuota")
	val kuota: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("durasi")
	val durasi: Int? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable
