package com.example.animalcrossinghelper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    var category = Category.Fish

    enum class Category {
        Fish,
        Bug,
        SeaCreature,
        Fossil
    }

    var updateRecycler = MutableLiveData<Int>()
}