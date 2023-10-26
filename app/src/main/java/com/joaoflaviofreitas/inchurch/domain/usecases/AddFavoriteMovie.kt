package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId

interface AddFavoriteMovie {

    suspend fun execute(favoriteMovieId: FavoriteMovieId)
}
