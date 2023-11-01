package com.joaoflaviofreitas.inchurch.data.mapper

import com.joaoflaviofreitas.inchurch.data.remote.model.MovieDto
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.utils.decimalFormat
import java.text.SimpleDateFormat
import java.util.Date

fun mapMovieDto(input: MovieDto): Movie {
    return Movie(
        id = input.id ?: 0,
        backdropPath = input.backdropPath ?: "",
        genres = input.genres,
        adult = input.adult ?: true,
        posterPath = input.posterPath ?: "",
        overview = input.overview.orEmpty(),
        releaseDate = if (!input.releaseDate.isNullOrEmpty()) {
            SimpleDateFormat("yyyy-MM-dd").parse(
                input.releaseDate,
            )
        } else {
            Date()
        },
        originalTitle = input.originalTitle.orEmpty(),
        originalLanguage = input.originalLanguage.orEmpty(),
        title = input.title.orEmpty(),
        popularity = input.popularity ?: 0.0,
        voteCount = input.voteCount ?: 0,
        video = input.video ?: false,
        voteAverage = decimalFormat.format(input.voteAverage).replace(",", ".").toDouble(),
    )
}
