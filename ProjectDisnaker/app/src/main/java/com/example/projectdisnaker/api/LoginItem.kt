package com.example.projectdisnaker.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginItem(
    val username: String? = null,
    val password: String? = null,
    val nama: String? = null,
    val role: Int? = null,
) : Parcelable