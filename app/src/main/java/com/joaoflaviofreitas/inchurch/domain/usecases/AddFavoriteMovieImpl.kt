package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository

class AddFavoriteMovieImpl(private val repository: MovieRepository) : AddFavoriteMovie {
    override suspend fun execute(favoriteMovieId: FavoriteMovieId) {
        repository.insertFavoriteMovie(favoriteMovieId)
    }
}
