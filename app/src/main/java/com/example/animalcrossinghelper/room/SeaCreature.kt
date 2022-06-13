package com.example.animalcrossinghelper.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SeaCreature(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String,
    var monthArray: MutableList<String>,
    var timeArray: MutableList<String>,
    var price: String,
    var icon_uri: String
)