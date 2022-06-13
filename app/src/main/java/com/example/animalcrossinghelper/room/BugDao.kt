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

}