package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.model.FavoriteMovieId

interface DeleteFavoriteMovie {

    suspend fun execute(favoriteMovieId: FavoriteMovieId)
}
