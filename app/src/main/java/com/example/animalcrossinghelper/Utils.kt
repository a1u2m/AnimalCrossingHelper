package com.example.animalcrossinghelper

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun closeKeyboard(activity: AppCompatActivity) {
    val view = activity.currentFocus
    val iMManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (view != null) {
        iMManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}