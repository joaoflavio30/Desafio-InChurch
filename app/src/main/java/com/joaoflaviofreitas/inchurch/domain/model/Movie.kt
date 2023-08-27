package com.joaoflaviofreitas.inchurch.domain.model

import java.io.Serializable
import java.util.Date

data class Movie(
    val id: Int,
    val posterPath: String,
    val isAdult: Boolean,
    val overview: String,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val releaseDate: Date,
    val popularity: Double,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Double,
    val backdropPath: String,
    val genres: List<Genre>?
): Serializable {
    var isFavorite: Boolean = false

}