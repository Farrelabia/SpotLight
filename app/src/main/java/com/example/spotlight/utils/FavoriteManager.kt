package com.example.spotlight.utils

import android.content.Context
import android.content.SharedPreferences

object FavoriteManager {
    private const val PREFS_NAME = "spotlight_prefs"
    private const val KEY_FAVORITES = "favorites"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isFavorite(context: Context, placeName: String): Boolean {
        return getPrefs(context).getStringSet(KEY_FAVORITES, emptySet())?.contains(placeName) == true
    }

    fun addFavorite(context: Context, placeName: String) {
        val prefs = getPrefs(context)
        val current = prefs.getStringSet(KEY_FAVORITES, emptySet())?.toMutableSet() ?: mutableSetOf()
        current.add(placeName)
        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
    }

    fun removeFavorite(context: Context, placeName: String) {
        val prefs = getPrefs(context)
        val current = prefs.getStringSet(KEY_FAVORITES, emptySet())?.toMutableSet() ?: mutableSetOf()
        current.remove(placeName)
        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
    }
}
