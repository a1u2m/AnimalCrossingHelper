package com.example.animalcrossinghelper.room

import androidx.room.*

@Dao
interface FossilDao {

    @Insert
    fun insert(fossil: Fossil)

    @Update
    fun update(fossil: Fossil)

    @Delete
    fun delete(fossil: Fossil)

    @Query("SELECT * FROM fossil")
    fun getAll(): List<Fossil>

    @Query("SELECT * FROM fish WHERE userId = 0")
    fun getPrimaryBase(): List<Fossil>

}