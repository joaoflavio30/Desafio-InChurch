package com.joaoflaviofreitas.inchurch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId

@Database(entities = [FavoriteMovieId::class], version = 1, exportSchema = false)
abstract class FavoriteMovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}