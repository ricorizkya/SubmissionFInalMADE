package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movies(
    val id: Int,
    val title: String,
    val description: String,
    val timeRelease: String,
    val rating: String,
    val imgPhoto: String,
    val isFavorite: Boolean
): Parcelable
