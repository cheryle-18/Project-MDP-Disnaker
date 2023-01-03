package com.example.projectdisnaker.api

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class StatusPendaftaranResponse(

	@field:SerializedName("pendaftaran")
	val pendaftaran: List<StatusItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class StatusItem(

	@field:SerializedName("pelatihan_id")
	val pelatihanId: Int? = null,

	@field:SerializedName("pelatihan")
	val pelatihan: String? = null,

	@field:SerializedName("peserta")
	val peserta: String? = null,

	@field:SerializedName("tgl_pendaftaran")
	val tglPendaftaran: String? = null,

	@field:SerializedName("status_pendaftaran")
	val statusPendaftaran: Int? = null,

	@field:SerializedName("status_kelulusan")
	val statusKelulusan: Int? = null,

	@field:SerializedName("tgl_wawancara")
	val tglWawancara: String? = null
) : Parcelable
