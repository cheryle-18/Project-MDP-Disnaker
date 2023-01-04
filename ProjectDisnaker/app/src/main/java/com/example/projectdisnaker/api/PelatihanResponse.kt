package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PelatihanResponse(

	@field:SerializedName("pelatihan")
	var pelatihan: List<PelatihanItem>? = null,

	@field:SerializedName("message")
	var message: String? = null
) : Parcelable

@Parcelize
data class PeluangItem(

	@field:SerializedName("kategori_id")
	var kategoriId: Int? = null,

	@field:SerializedName("keterangan")
	var keterangan: String? = null,

	@field:SerializedName("nama")
	var nama: String? = null,

	@field:SerializedName("updated_at")
	var updatedAt: String? = null,

	@field:SerializedName("perusahaan_id")
	var perusahaanId: Int? = null,

	@field:SerializedName("kuota")
	var kuota: Int? = null,

	@field:SerializedName("lowongan_id")
	var lowonganId: Int? = null,

	@field:SerializedName("created_at")
	var createdAt: String? = null,

	@field:SerializedName("deleted_at")
	var deletedAt: String? = null,

	@field:SerializedName("status")
	var status: Int? = null
) : Parcelable

@Parcelize
data class PelatihanSyaratItem(

	@field:SerializedName("deskripsi")
	var deskripsi: String? = null,

	@field:SerializedName("pelatihan_id")
	var pelatihanId: Int? = null,

	@field:SerializedName("updated_at")
	var updatedAt: String? = null,

	@field:SerializedName("sp_id")
	var spId: Int? = null,

	@field:SerializedName("created_at")
	var createdAt: String? = null,

	@field:SerializedName("deleted_at")
	var deletedAt: String? = null
) : Parcelable

@Parcelize
data class PelatihanItem(

	@field:SerializedName("pelatihan_id")
	var pelatihanId: Int? = null,

	@field:SerializedName("keterangan")
	var keterangan: String? = null,

	@field:SerializedName("nama")
	var nama: String? = null,

	@field:SerializedName("pendidikan")
	var pendidikan: String? = null,

	@field:SerializedName("kuota")
	var kuota: Int? = null,

	@field:SerializedName("kategori")
	var kategori: String? = null,

	@field:SerializedName("durasi")
	var durasi: Int? = null,

	@field:SerializedName("syarat")
	var syarat: List<PelatihanSyaratItem>? = null,

	@field:SerializedName("peluang")
	var peluang: List<PeluangItem>? = null,

	@field:SerializedName("status")
	var status: Int? = null,

	@field:SerializedName("stat")
	var stat: Int? = -1,
) : Parcelable
