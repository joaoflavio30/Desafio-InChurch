package com.joaoflaviofreitas.inchurch.utils // ktlint-disable filename

import com.joaoflaviofreitas.inchurch.data.model.ResponseGenre
import com.joaoflaviofreitas.inchurch.data.model.ResponseMovie
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val decimalFormat = DecimalFormat("#.#")
val displayDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

fun ResponseMovie.toMovie(): Movie {
    return Movie(
        id = this.id,
        backdropPath = this.backdrop_path ?: "",
        genres = this.genres,
        isAdult = this.adult,
        posterPath = this.poster_path ?: "",
        overview = this.overview,
        releaseDate = if (!this.release_date.isNullOrEmpty()) {
            SimpleDateFormat("yyyy-MM-dd").parse(
                release_date,
            )
        } else {
            Date()
        },
        originalTitle = this.original_title,
        originalLanguage = this.original_language,
        title = this.title,
        popularity = this.popularity,
        voteCount = this.vote_count,
        video = this.video,
        voteAverage = decimalFormat.format(this.vote_average).toDouble(),
    )
}

fun ResponseGenre.toGenre(): Genre {
    return Genre(
        id = this.id,
        name = this.name,
    )
}
