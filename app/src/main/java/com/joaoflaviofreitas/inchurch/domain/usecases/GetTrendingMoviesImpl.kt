package com.joaoflaviofreitas.inchurch.domain.usecases

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingMoviesImpl(private val repository: MovieRepository) : GetTrendingMovies {
    override fun execute(page: Int): Flow<PagingData<Movie>> =
        repository.getTrendingMovies(page)
}
