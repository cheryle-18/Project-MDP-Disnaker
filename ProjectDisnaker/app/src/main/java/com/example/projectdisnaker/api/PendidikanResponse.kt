package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PendidikanResponse(

	@field:SerializedName("pendidikan")
	val pendidikan: List<PendidikanItem>? = null
) : Parcelable

@Parcelize
data class PendidikanItem(

	@field:SerializedName("pendidikan")
	val pendidikan: String? = null,

	@field:SerializedName("jurusan")
	val jurusan: String? = null,

	@field:SerializedName("nilai")
	val nilai: Int? = null,

	@field:SerializedName("pendidikan_id")
	val pendidikanId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null

) : Parcelable {
	override fun toString(): String {
		return nama!!
	}
}
