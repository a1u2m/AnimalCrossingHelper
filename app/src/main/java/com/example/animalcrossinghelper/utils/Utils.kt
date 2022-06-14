package com.example.animalcrossinghelper.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.animalcrossinghelper.R
import com.example.animalcrossinghelper.room.User
import com.example.animalcrossinghelper.room.UserDao
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun closeKeyboard(activity: AppCompatActivity) {
    val view = activity.currentFocus
    val iMManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (view != null) {
        iMManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun checkPasswordsForEquality( //todo эти 3 метода отсюда и из StartFragment надо бы перенести в утилсы
    password: String,
    passwordRepeat: String,
    view: View
): Boolean {
    if (password != passwordRepeat) {
        Snackbar.make(view, R.string.passwords_are_not_equal, Snackbar.LENGTH_LONG)
            .show()
        return true
    }
    return false
}

fun checkFieldsForEmptiness(
    login: String,
    password: String,
    passwordRepeat: String,
    view: View
): Boolean {
    if (login.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()) {
        Snackbar.make(
            view,
            R.string.login_and_passwords_must_not_be_empty,
            Snackbar.LENGTH_LONG
        ).show()
        return true
    }
    return false
}

fun checkFieldsForEmptiness(
    login: String,
    password: String,
    view: View
): Boolean {
    if (login.isEmpty() || password.isEmpty()) {
        Snackbar.make(
            view,
            R.string.login_and_passwords_must_not_be_empty,
            Snackbar.LENGTH_LONG
        ).show()
        return true
    }
    return false
}

fun getUsersList(list: List<User>, userDao: UserDao): List<User> {
    var usersList = list
    runBlocking {
        launch {
            withContext(Dispatchers.IO) {
                usersList =
                    userDao.getAll() //todo посмотреть как заменить это на реактивщину
            }
        }
    }
    return usersList
}

fun checkLoginForExisting(login: String, view: View, userDao: UserDao): Boolean {
    var list: List<User> = listOf()
    list = getUsersList(list, userDao)
    for (i in list) {
        if (login == i.login) {
            Snackbar.make(view, R.string.user_already_exists, Snackbar.LENGTH_LONG)
                .show()
            return true
        }
    }
    return false
}

fun checkLoginForExisting(login: String, id: Long, view: View, userDao: UserDao): Boolean {
    var list: List<User> = listOf()
    list = getUsersList(list, userDao)
    for (i in list) {
        if (login == i.login && id != i.id) {
            Snackbar.make(view, R.string.user_already_exists, Snackbar.LENGTH_LONG)
                .show()
            return true
        }
    }
    return false
}

fun checkFieldsForChange(login: String, password: String, view: View, userDao: UserDao): Boolean {
    var list: List<User> = listOf()
    list = getUsersList(list, userDao)
    for (i in list) {
        if (login == i.login && password == i.password) {
            Snackbar.make(view, R.string.field_is_not_changed, Snackbar.LENGTH_LONG)
                .show()
            return true
        }
    }
    return false
}