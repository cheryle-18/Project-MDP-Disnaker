package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

@Parcelize
data class PerusahaanResponse(

	@field:SerializedName("perusahaan")
	val perusahaan: List<PerusahaanItem?>? = null
) : Parcelable

@Parcelize
data class PerusahaanItem(

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

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
) : Parcelable

@Parcelize
data class User(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("role")
	val role: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Timestamp? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: Timestamp? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Timestamp? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
