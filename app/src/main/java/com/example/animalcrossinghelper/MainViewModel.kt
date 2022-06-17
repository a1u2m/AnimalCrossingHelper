package com.example.animalcrossinghelper

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import kotlin.math.log

class MainViewModel @Inject constructor() : ViewModel() {

    val category = MutableLiveData<Category>()

    enum class Category {
        fish,
        bug,
        seaCreature,
        fossil
    }
}