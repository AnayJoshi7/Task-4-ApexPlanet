package com.anay.fitnesstracker.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("fitness_tracker_prefs", Context.MODE_PRIVATE)

    fun saveUsername(username: String) {
        prefs.edit().putString("LOGGED_IN_USERNAME", username).apply()
    }

    fun getUsername(): String? {
        return prefs.getString("LOGGED_IN_USERNAME", null)
    }

    fun clearSession() {
        prefs.edit().remove("LOGGED_IN_USERNAME").apply()
    }
}