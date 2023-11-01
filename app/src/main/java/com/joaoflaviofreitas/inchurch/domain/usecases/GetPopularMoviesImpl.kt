package com.joaoflaviofreitas.inchurch.domain.usecases

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesImpl(private val repository: MovieRepository) : GetPopularMovies {
    override fun execute(page: Int): Flow<PagingData<Movie>> =
        repository.getPopularMovies(page)
}
