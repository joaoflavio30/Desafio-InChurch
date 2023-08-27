package com.joaoflaviofreitas.inchurch.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class FavoriteMovieId(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
)
