package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository

class AddFavoriteMovie(private val repository: MovieRepository) {
    suspend fun execute(favoriteMovieId: FavoriteMovieId) {
        repository.insertFavoriteMovie(favoriteMovieId)
    }
}
