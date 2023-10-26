package com.joaoflaviofreitas.inchurch.domain.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.utils.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUpcomingMoviesImpl(private val repository: MovieRepository): GetUpcomingMovies {
    override fun execute(page: Int): Flow<PagingData<Movie>> =
        repository.getUpcomingMovies(page).map {
            it.map { responseMovie ->
                responseMovie.toMovie()
            }
        }
}
