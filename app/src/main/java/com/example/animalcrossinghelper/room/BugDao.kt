package com.example.animalcrossinghelper.room

import androidx.room.*

@Dao
interface BugDao {

    @Insert
    fun insert(bug: Bug)

    @Update
    fun update(bug: Bug)

    @Delete
    fun delete(bug: Bug)

    @Query("SELECT * FROM bug")
    fun getAll(): List<Bug>

    @Query("SELECT * FROM bug WHERE userId = 0")
    fun getPrimaryBase(): List<Bug>

    @Query("DELETE FROM bug WHERE id = :id")
    fun deleteById(id: Long)
}