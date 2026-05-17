package com.example.spotlight.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class PlaceCategory {
    CAFE, MUSEUM, KULINER, TAMAN
}

@Parcelize
data class Place(
    val name: String,
    val category: PlaceCategory,
    val location: String,
    val rating: Float,
    val imageRes: Int,
    val mapUrl: String
) : Parcelable
