package com.example.animalcrossinghelper.room

import androidx.room.*

@Dao
interface FishDao {

    @Insert
    fun insert(fish: Fish)

    @Update
    fun update(fish: Fish)

    @Delete
    fun delete(fish: Fish)

    @Query("SELECT * FROM fish")
    fun getAll(): List<Fish>

    @Query("SELECT * FROM fish WHERE userId = 0")
    fun getPrimaryBase(): List<Fish>

    @Query("SELECT * FROM fish WHERE userId = :id")
    fun getUserBase(id: Long): List<Fish>

    @Query("DELETE FROM fish WHERE userId = :userId AND id = :id")
    fun deleteFromUserById(userId: Long, id: Long)
}