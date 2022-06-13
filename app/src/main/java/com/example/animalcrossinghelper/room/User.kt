package com.example.animalcrossinghelper.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    @PrimaryKey var id: Long,
    var login: String,
    var password: String,
)