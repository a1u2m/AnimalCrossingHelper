package com.example.animalcrossinghelper

import android.app.Application
import androidx.room.Room
import com.example.animalcrossinghelper.room.AppDatabase

class App : Application() {

    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .build()
    }

    fun getDatabase(): AppDatabase {
        return database
    }
}