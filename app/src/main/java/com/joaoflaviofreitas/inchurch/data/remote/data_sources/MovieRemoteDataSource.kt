package com.joaoflaviofreitas.inchurch.data.remote.data_sources

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.remote.model.GenresDto
import com.joaoflaviofreitas.inchurch.data.remote.model.MovieDto
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {

    fun getTrendingMovies(page: Int): Flow<PagingData<MovieDto>>

    fun getPopularMovies(page: Int): Flow<PagingData<MovieDto>>

    fun getUpcomingMovies(page: Int): Flow<PagingData<MovieDto>>

    fun searchMoviesByTerm(page: Int, query: String): Flow<PagingData<MovieDto>>

    suspend fun getGenres(): GenresDto

    suspend fun getMovieDetails(id: Int): MovieDto
}
