package com.joaoflaviofreitas.inchurch.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class FavoriteMovieIdEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
)
