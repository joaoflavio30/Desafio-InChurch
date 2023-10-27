package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class SearchFavoriteMoviesByTermImpl(private val repository: MovieRepository) : SearchFavoriteMoviesByTerm {

    override fun execute(term: String): Flow<List<FavoriteMovieId>> = repository.searchFavoriteMoviesByTerm(term)
}
