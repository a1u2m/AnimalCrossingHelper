package com.example.animalcrossinghelper.utils

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import java.util.*

fun TextInputEditText.show() {
    inputType =
        InputType.TYPE_CLASS_TEXT
}

fun TextInputEditText.hide() {
    inputType =
        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
}

fun TextInputEditText.moveCursorToEnd(editText: TextInputEditText) {
    editText.text?.let { setSelection(it.length) }
}

fun Fragment.navigate(resId: Int, bundle: Bundle? = null) {
    NavHostFragment.findNavController(this).navigate(resId, bundle)
}