package com.example.animalcrossinghelper.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Fossil(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String,
    var price: String,
    var userId: Long
)