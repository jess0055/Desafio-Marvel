package com.example.marvelhq.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Comic::class], version = 1)
abstract class ComicsDatabase: RoomDatabase() {
    abstract fun comicsDao() : ComicsDao
}