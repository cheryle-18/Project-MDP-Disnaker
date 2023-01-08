package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PesertaPendaftaranResponse(

	@field:SerializedName("pendaftaran")
	val pendaftaran: List<PesertaPendaftaranItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class PesertaPendaftaranItem(

	@field:SerializedName("pl_id")
	val plId: Int? = null,

	@field:SerializedName("pp_id")
	val ppId: Int? = null,

	@field:SerializedName("telp")
	val telp: String? = null,

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

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("tgl_lahir")
	val tglLahir: String? = null,

	@field:SerializedName("usia")
	val usia: Int? = null,

	@field:SerializedName("ijazah")
	val ijazah: String? = null,

	@field:SerializedName("status_pendaftaran")
	val status_pendaftaran: Int? = null,

	@field:SerializedName("status_kelulusan")
	val status_kelulusan: Int? = null
) : Parcelable
