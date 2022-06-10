package com.example.animalcrossinghelper

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreferencesHelper(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("SharedPreferencesHelper", MODE_PRIVATE)

    fun putRememberMe(isEnabled: Boolean) {
        sharedPreferences.edit()
            .putBoolean(context.getString(R.string.is_remember_me_key), isEnabled)
            .apply()
    }

    fun getRememberMe(): Boolean {
        return sharedPreferences.getBoolean(
            context.getString(R.string.is_remember_me_key),
            true
        )
    }

    fun putIsLogged(isLogged: Boolean) {
        sharedPreferences.edit()
            .putBoolean(context.getString(R.string.is_logged_key), isLogged)
            .apply()
    }

    fun getIsLogged(): Boolean {
        return sharedPreferences.getBoolean(
            context.getString(R.string.is_logged_key),
            true
        )
    }


}