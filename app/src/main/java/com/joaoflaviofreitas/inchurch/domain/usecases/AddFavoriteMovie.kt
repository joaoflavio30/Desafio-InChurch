package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.model.FavoriteMovieId

interface AddFavoriteMovie {

    suspend fun execute(favoriteMovieId: FavoriteMovieId)
}
