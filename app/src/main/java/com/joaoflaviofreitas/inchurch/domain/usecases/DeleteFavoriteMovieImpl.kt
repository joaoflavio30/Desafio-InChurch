package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository

class DeleteFavoriteMovieImpl(private val repository: MovieRepository): DeleteFavoriteMovie {

    override suspend fun execute(favoriteMovieId: FavoriteMovieId) {
        repository.deleteFavoriteMovie(favoriteMovieId)
    }
}
