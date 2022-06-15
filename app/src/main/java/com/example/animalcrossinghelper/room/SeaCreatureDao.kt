package com.example.animalcrossinghelper.room

import androidx.room.*

@Dao
interface SeaCreatureDao {

    @Insert
    fun insert(seaCreature: SeaCreature)

    @Update
    fun update(seaCreature: SeaCreature)

    @Delete
    fun delete(seaCreature: SeaCreature)

    @Query("SELECT * FROM seacreature")
    fun getAll(): List<SeaCreature>

    @Query("SELECT * FROM fish WHERE userId = 0")
    fun getPrimaryBase(): List<Fish>

}