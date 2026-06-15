package com.dsdm.fitro.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("fitro_prefs", Context.MODE_PRIVATE)

    fun saveUser(email: String, password: String) {
        prefs.edit()
            .putString("user_email", email)
            .putString("user_password", password)
            .apply()
    }

    fun getEmail(): String = prefs.getString("user_email", "") ?: ""
    fun getPassword(): String = prefs.getString("user_password", "") ?: ""

    fun setLoggedIn(loggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", loggedIn).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)

    fun saveDisplayName(name: String) {
        prefs.edit().putString("display_name", name).apply()
    }

    fun getDisplayName(): String = prefs.getString("display_name", "") ?: ""

    fun logout() {
        prefs.edit().putBoolean("is_logged_in", false).apply()
    }
}
