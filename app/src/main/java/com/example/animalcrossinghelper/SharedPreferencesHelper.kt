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

    fun putNextFreeId(id: Long) {
        sharedPreferences.edit()
            .putLong(context.getString(R.string.id_key), id + 1)
            .apply()
    }

    fun getNextFreeId(): Long {
        return sharedPreferences.getLong(
            context.getString(R.string.id_key),
            0
        )
    }

    fun putActualLogin(login: String) { //todo возможно там где это используется стоит доставать логин по айдишнику (парой методов ниже)
        sharedPreferences.edit().putString(context.getString(R.string.login_key), login).apply()
    }

    fun getActualLogin(): String {  //todo возможно не нужно из-за тудушки выше
        return sharedPreferences.getString(context.getString(R.string.login_key), "")!!
    }

    fun putIdOfLoggedUser(id: Long) {
        sharedPreferences.edit().putLong(context.getString(R.string.logged_id_key), id).apply()
    }

    fun getIdOfLoggedUser(): Long {
        return sharedPreferences.getLong(context.getString(R.string.logged_id_key), 0)
    }

}