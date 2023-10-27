package com.joaoflaviofreitas.inchurch.data.remote.model

import com.google.gson.annotations.SerializedName
import com.joaoflaviofreitas.inchurch.domain.model.Genre

data class ResponseMovie(
    @SerializedName("poster_path") val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String?,
    val genres: List<Genre>?,
    val id: Int,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    val title: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val popularity: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
)
