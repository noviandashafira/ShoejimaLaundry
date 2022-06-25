package com.dicoding.picodiploma.mynotesapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Note(
    var id : Int = 0,
    var name: String? = null,
    var email: String? = null,
    var date: String? = null
): Parcelable
