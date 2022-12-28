package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class KategoriResponse(

	@field:SerializedName("kategori")
	val kategori: List<KategoriItem>? = null
) : Parcelable

@Parcelize
data class KategoriItem(

	@field:SerializedName("kategori_id")
	val kategoriId: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: String? = null


) : Parcelable {
	override fun toString(): String {
		return nama!!
	}
}
