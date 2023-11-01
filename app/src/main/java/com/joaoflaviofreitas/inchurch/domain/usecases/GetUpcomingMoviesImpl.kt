package com.joaoflaviofreitas.inchurch.domain.usecases

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetUpcomingMoviesImpl(private val repository: MovieRepository) : GetUpcomingMovies {
    override fun execute(page: Int): Flow<PagingData<Movie>> =
        repository.getUpcomingMovies(page)
}
