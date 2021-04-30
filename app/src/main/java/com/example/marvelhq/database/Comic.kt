package com.example.marvelhq.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comic(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val idApi: Int
)