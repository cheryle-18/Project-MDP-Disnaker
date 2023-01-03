package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PelatihanResponse(

	@field:SerializedName("pelatihan")
	val pelatihan: List<PelatihanItem>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class PeluangItem(

	@field:SerializedName("kategori_id")
	val kategoriId: Int? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("perusahaan_id")
	val perusahaanId: Int? = null,

	@field:SerializedName("kuota")
	val kuota: Int? = null,

	@field:SerializedName("lowongan_id")
	val lowonganId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class PelatihanItem(

	@field:SerializedName("pelatihan_id")
	val pelatihanId: Int? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("pendidikan")
	val pendidikan: String? = null,


	@field:SerializedName("kuota")
	val kuota: Int? = null,

	@field:SerializedName("kategori")
	val kategori: String? = null,

	@field:SerializedName("durasi")
	val durasi: Int? = null,

	@field:SerializedName("syarat")
	val syarat: List<PelatihanSyaratItem>? = null,

	@field:SerializedName("peluang")
	val peluang: List<PeluangItem>? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class PelatihanSyaratItem(

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("pelatihan_id")
	val pelatihanId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("sp_id")
	val spId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null
) : Parcelable
