package com.example.animalcrossinghelper.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Bug(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String,
    var location: String,
    var rarity: String,
    var monthArray: MutableList<String>,
    var timeArray: MutableList<String>,
    var price: String,
    var iconUri: String,
    var userId: Long
)