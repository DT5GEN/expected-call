package com.dt5gen.expectedcall.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val name: String,
    val phoneNumber: String,
    val ringtoneUri: String? = null
) : Parcelable