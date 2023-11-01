package com.joaoflaviofreitas.inchurch.domain.usecases

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesByTermImpl(private val repository: MovieRepository) : SearchMoviesByTerm {

    override fun execute(page: Int, query: String): Flow<PagingData<Movie>> =
        repository.searchMoviesByTerm(page, query)
}
