package com.example.animalcrossinghelper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User (
    @PrimaryKey var id: Long,
    var login: String,
    var password: String,
    var rememberMe: Boolean
        )