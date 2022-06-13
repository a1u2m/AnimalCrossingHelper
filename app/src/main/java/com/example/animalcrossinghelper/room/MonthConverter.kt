package com.example.animalcrossinghelper.room

import androidx.room.TypeConverter
import java.util.stream.Collectors

class MonthConverter {

    @TypeConverter
    fun fromMonth(list: MutableList<String>): String {
        return list.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toMonth(data: String): MutableList<String> {
        return data.split(",").toMutableList()
    }
}