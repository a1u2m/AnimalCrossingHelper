package com.example.animalcrossinghelper.model

import com.google.gson.annotations.SerializedName

data class FishAvailability(
    var location: String? = null,
    var rarity: String? = null,
    @SerializedName("month-array-northern")
    var month_array_northern: List<String>? = null,
    @SerializedName("time-array")
    var time_array: List<String>? = null
)