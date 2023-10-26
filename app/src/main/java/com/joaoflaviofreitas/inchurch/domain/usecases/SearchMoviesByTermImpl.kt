package com.joaoflaviofreitas.inchurch.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.utils.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchMoviesByTermImpl(private val repository: MovieRepository) : SearchMoviesByTerm {

    override fun execute(page: Int, query: String): Flow<PagingData<Movie>> =
        repository.searchMoviesByTerm(page, query).map {
            it.map { responseMovie ->
                responseMovie.toMovie()
            }
        }
}
