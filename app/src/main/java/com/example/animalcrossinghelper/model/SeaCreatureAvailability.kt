package com.example.animalcrossinghelper.model

import com.google.gson.annotations.SerializedName

data class SeaCreatureAvailability(
    @SerializedName("month-array-northern")
    var month_array_northern: MutableList<String>? = null,
    @SerializedName("time-array")
    var time_array: MutableList<String>? = null
)