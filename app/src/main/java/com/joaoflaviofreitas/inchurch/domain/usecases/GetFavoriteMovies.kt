package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteMovies(private val repository: MovieRepository) {

    fun execute(): Flow<List<FavoriteMovieId>> =
        repository.getFavoriteMovies()
}
