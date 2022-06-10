package com.example.animalcrossinghelper

import android.text.InputType
import com.google.android.material.textfield.TextInputEditText

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