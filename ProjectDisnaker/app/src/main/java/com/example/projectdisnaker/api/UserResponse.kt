package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("userResponse")
	val userResponse: List<UserResponseItem?>? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class UserResponseItem(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("role")
	val role: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("perusahaan_id")
	val perusahaanId: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("pendidikan")
	val pendidikan: String? = null,

	@field:SerializedName("nilai")
	val nilai: Int? = null,

	@field:SerializedName("jurusan")
	val jurusan: String? = null,

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("peserta_id")
	val pesertaId: Int? = null,

	@field:SerializedName("tgl_lahir")
	val tglLahir: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("usia")
	val usia: Int? = null,

	@field:SerializedName("ijazah")
	val ijazah: String? = null,

	@field:SerializedName("api_key")
	val apiKey: String? = null,

	@field:SerializedName("listLowongan")
	val listLowongan: List<LowonganItem>? = null

) : Parcelable
