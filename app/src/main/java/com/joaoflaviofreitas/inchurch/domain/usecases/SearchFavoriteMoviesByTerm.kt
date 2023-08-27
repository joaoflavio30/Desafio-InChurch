package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class SearchFavoriteMoviesByTerm(private val repository: MovieRepository) {

    fun execute(term: String): Flow<List<FavoriteMovieId>> = repository.searchFavoriteMoviesByTerm(term)
}
