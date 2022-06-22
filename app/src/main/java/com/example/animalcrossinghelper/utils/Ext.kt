package com.example.animalcrossinghelper.utils

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.animalcrossinghelper.R
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
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

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun load(pictureLink: String, icon: AppCompatImageView) {
    Picasso.get()
            .load(pictureLink)
            .error(R.drawable.ic_placeholder)
            .into(icon)
}