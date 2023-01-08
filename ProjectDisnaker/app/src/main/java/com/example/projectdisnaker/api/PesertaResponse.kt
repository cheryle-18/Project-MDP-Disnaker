package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PesertaResponse(
	@field:SerializedName("peserta")
	val peserta: List<PesertaItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

) : Parcelable

@Parcelize
data class PesertaItem(

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("tgl_lahir")
	val tglLahir: String? = null,

	@field:SerializedName("peserta_id")
	val pesertaId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("pendidikan")
	val pendidikan: String? = null,

	@field:SerializedName("nilai")
	val nilai: Int? = null,

	@field:SerializedName("jurusan")
	val jurusan: String? = null,

	@field:SerializedName("ijazah")
	val ijazah: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,
) : Parcelable
