package com.example.spotlight.datasource

import com.example.spotlight.R
import com.example.spotlight.model.Place
import com.example.spotlight.model.PlaceCategory

object DataSource {

    private val allPlaces = listOf(
        Place(
            "Kopi Nako Depok", PlaceCategory.CAFE, "Depok", 4.4f, R.drawable.kopi_nako_depok,
            "https://maps.google.com/?q=Kopi+Nako+Depok"
        ),
        Place(
            "MATCHAMAN, Blok M", PlaceCategory.CAFE, "Jakarta Selatan", 4.7f, R.drawable.matchaman_blok_m,
            "https://maps.google.com/?q=MATCHAMAN+Blok+M"
        ),
        Place(
            "Museum MACAN", PlaceCategory.MUSEUM, "Jakarta Barat", 4.8f, R.drawable.museum_macan,
            "https://maps.google.com/?q=Museum+MACAN+Jakarta"
        ),
        Place(
            "Museum Nasional", PlaceCategory.MUSEUM, "Jakarta Pusat", 4.6f, R.drawable.museum_nasional,
            "https://maps.google.com/?q=Museum+Nasional+Jakarta"
        ),
        Place(
            "Obihiro nikudon", PlaceCategory.KULINER, "Jakarta Selatan", 4.6f, R.drawable.obihiro_nikudon,
            "https://maps.google.com/?q=Obihiro+nikudon+Jakarta"
        ),
        Place(
            "Waduk Brigif", PlaceCategory.TAMAN, "Depok", 4.7f, R.drawable.waduk_brigif,
            "https://maps.google.com/?q=Waduk+Brigif+Depok"
        ),
        Place(
            "Taman Ismail Marzuki", PlaceCategory.TAMAN, "Jakarta Pusat", 4.5f, R.drawable.taman_ismail_marzuki,
            "https://maps.google.com/?q=Taman+Ismail+Marzuki+Jakarta"
        )
    )

    fun getPlaces(category: String? = null, sortBy: String = "rating_desc"): List<Place> {
        var result = if (category != null) {
            allPlaces.filter { it.category.name == category }
        } else {
            allPlaces
        }

        result = when (sortBy) {
            "rating_desc" -> result.sortedByDescending { it.rating }
            "rating_asc" -> result.sortedBy { it.rating }
            "name_asc" -> result.sortedBy { it.name }
            "name_desc" -> result.sortedByDescending { it.name }
            else -> result.sortedByDescending { it.rating }
        }

        return result
    }
}
