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

    @Query("SELECT * FROM bug WHERE userId = :id")
    fun getUserBase(id: Long): List<Bug>

    @Query("DELETE FROM bug WHERE userId = :userId AND id = :id")
    fun deleteFromUserById(userId: Long, id: Long)
}