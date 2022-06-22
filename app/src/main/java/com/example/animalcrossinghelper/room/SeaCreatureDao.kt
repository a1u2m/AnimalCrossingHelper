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

    @Query("SELECT * FROM seacreature WHERE userId = 0")
    fun getPrimaryBase(): List<SeaCreature>

    @Query("SELECT * FROM seacreature WHERE userId = :id")
    fun getUserBase(id: Long): List<SeaCreature>

    @Query("DELETE FROM seacreature WHERE userId = :userId AND id = :id")
    fun deleteFromUserById(userId: Long, id: Long)
}