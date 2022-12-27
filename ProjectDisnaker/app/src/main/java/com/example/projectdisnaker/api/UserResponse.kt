package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserResponse(

	@field:SerializedName("UserResponse")
	val userResponse: List<UserResponseItem?>? = null
) : Parcelable

@Parcelize
data class UserResponseItem(

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("telp")
	val telp: String? = null,

	@field:SerializedName("role")
	val role: Int? = null


) : Parcelable
