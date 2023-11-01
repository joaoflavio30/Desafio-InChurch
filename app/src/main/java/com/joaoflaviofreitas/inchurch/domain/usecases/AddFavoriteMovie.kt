package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId

interface AddFavoriteMovie {

    suspend fun execute(favoriteMovieId: FavoriteMovieId)
}
