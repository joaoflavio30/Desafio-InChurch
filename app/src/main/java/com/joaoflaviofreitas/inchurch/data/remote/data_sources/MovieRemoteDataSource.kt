package com.joaoflaviofreitas.inchurch.data.remote.data_sources

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseGenreApiDto
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseMovie
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {

    fun getTrendingMovies(page: Int): Flow<PagingData<ResponseMovie>>

    fun getPopularMovies(page: Int): Flow<PagingData<ResponseMovie>>

    fun getUpcomingMovies(page: Int): Flow<PagingData<ResponseMovie>>

    fun searchMoviesByTerm(page: Int, query: String): Flow<PagingData<ResponseMovie>>

    fun getGenres(): Flow<ResponseGenreApiDto>

    fun getMovieDetails(id: Int): Flow<ResponseMovie>
}
