package com.example.projectdisnaker.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PasswordItem(
    val passbaru: String? = null,
    val passlama: String? = null,
) : Parcelable