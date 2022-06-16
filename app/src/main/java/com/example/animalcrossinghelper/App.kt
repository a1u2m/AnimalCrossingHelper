package com.example.animalcrossinghelper

import android.app.Application
import androidx.room.Room
import com.example.animalcrossinghelper.di.MainModule
import com.example.animalcrossinghelper.room.AppDatabase
import toothpick.Scope
import toothpick.Toothpick

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val appScope: Scope = Toothpick.openScope("APP")
        appScope.installModules(MainModule(applicationContext))
    }
}