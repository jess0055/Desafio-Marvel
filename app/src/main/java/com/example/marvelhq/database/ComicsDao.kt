package com.example.marvelhq.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ComicsDao {

    @Query("SELECT * FROM comic")
    suspend fun getAll(): List<Comic>

    @Query("SELECT * FROM comic WHERE idApi LIKE :idApi")
    suspend fun findById(idApi: Int): Comic

    @Insert
    suspend fun insertAll(vararg comics: Comic)

    @Delete
    suspend fun delete(vararg comics: Comic)
}