package com.joaoflaviofreitas.inchurch.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.utils.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTrendingMovies(private val repository: MovieRepository) {
    fun execute(page: Int = 1): Flow<PagingData<Movie>> =
        repository.getTrendingMovies(page).map {
            it.map { responseMovie ->
                responseMovie.toMovie()
            }
        }
}