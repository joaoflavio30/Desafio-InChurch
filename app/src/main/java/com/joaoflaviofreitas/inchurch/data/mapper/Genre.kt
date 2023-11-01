package com.joaoflaviofreitas.inchurch.data.mapper

import com.joaoflaviofreitas.inchurch.data.remote.model.GenreDto
import com.joaoflaviofreitas.inchurch.data.remote.model.GenresDto
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Genres

inline fun mapGenresDto(input: GenresDto, mapGenreDto: (List<GenreDto>?) -> (List<Genre>)): Genres {
    return Genres(
        mapGenreDto(input.genres),
    )
}

fun mapGenreDto(input: GenreDto): Genre =
    Genre(
        input.id ?: 0,
        input.name.orEmpty(),
    )
