package com.joaoflaviofreitas.inchurch.data.mapper

import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieIdEntity
import com.joaoflaviofreitas.inchurch.domain.model.FavoriteMovieId

fun mapFavoriteMovieId(input: FavoriteMovieId): FavoriteMovieIdEntity {
    return FavoriteMovieIdEntity(
        input.id,
        input.title,
    )
}

fun mapFavoriteMovieIdEntity(input: FavoriteMovieIdEntity): FavoriteMovieId {
    return FavoriteMovieId(
        input.id,
        input.title,
    )
}
