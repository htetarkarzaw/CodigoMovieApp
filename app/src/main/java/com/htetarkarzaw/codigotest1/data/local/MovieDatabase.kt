package com.htetarkarzaw.codigotest1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.htetarkarzaw.codigotest1.data.local.dao.MovieDao
import com.htetarkarzaw.codigotest1.data.local.entities.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}