package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserResponse(
	//name and value should be the same with return data from backend
	@field:SerializedName("userResponse")
	val userResponse: List<UserResponseItem>? = null,
	@field:SerializedName("message")
	val message: String = "",
	@field:SerializedName("status")
    val status: Int = -1
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
