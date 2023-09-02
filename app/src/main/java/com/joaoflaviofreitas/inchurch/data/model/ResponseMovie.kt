package com.joaoflaviofreitas.inchurch.data.model

import com.joaoflaviofreitas.inchurch.domain.model.Genre

data class ResponseMovie(
    val poster_path: String?,
    val adult: Boolean,
    val overview: String,
    val release_date: String?,
    val genres: List<Genre>?,
    val id: Int,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String?,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Double,
)
