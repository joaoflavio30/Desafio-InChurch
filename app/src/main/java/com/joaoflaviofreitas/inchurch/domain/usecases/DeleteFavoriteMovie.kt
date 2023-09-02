package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository

class DeleteFavoriteMovie(private val repository: MovieRepository) {

    suspend fun execute(favoriteMovieId: FavoriteMovieId) {
        repository.deleteFavoriteMovie(favoriteMovieId)
    }
}
