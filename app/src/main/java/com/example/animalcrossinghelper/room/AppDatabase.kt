package com.example.animalcrossinghelper.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [User::class, Fish::class, Bug::class, SeaCreature::class, Fossil::class],
    version = 1
)
@TypeConverters(MonthConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun fishDao(): FishDao
    abstract fun bugDao(): BugDao
    abstract fun seaCreatureDao(): SeaCreatureDao
    abstract fun fossilDao(): FossilDao

}