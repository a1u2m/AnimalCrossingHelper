package com.example.animalcrossinghelper.di

import android.content.Context
import androidx.room.Room
import com.example.animalcrossinghelper.MainViewModel
import com.example.animalcrossinghelper.SharedPreferencesHelper
import com.example.animalcrossinghelper.api.RetrofitHelper
import com.example.animalcrossinghelper.room.*
import toothpick.config.Module

class MainModule(context: Context) : Module() {

    init {
        val prefsHelper = SharedPreferencesHelper(context)
        bind(SharedPreferencesHelper::class.java).toInstance(prefsHelper)

        val db = Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .build()
        bind(AppDatabase::class.java).toInstance(db)

        val userDao = db.userDao()
        bind(UserDao::class.java).toInstance(userDao)

        val fishDao = db.fishDao()
        bind(FishDao::class.java).toInstance(fishDao)

        val bugDao = db.bugDao()
        bind(BugDao::class.java).toInstance(bugDao)

        val seaCreatureDao = db.seaCreatureDao()
        bind(SeaCreatureDao::class.java).toInstance(seaCreatureDao)

        val fossilDao = db.fossilDao()
        bind(FossilDao::class.java).toInstance(fossilDao)

        val retrofitHelper = RetrofitHelper()
        bind(RetrofitHelper::class.java).toInstance(retrofitHelper)

        bind(MainViewModel::class.java).singleton()

    }

}