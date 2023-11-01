package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteMoviesImpl(private val repository: MovieRepository) : GetFavoriteMovies {

    override fun execute(): Flow<List<FavoriteMovieId>> =
        repository.getFavoriteMovies()
}
