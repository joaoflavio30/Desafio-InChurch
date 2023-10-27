package com.joaoflaviofreitas.inchurch.domain.repository

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseGenre
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseMovie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrendingMovies(page: Int): Flow<PagingData<ResponseMovie>>
    fun getPopularMovies(page: Int): Flow<PagingData<ResponseMovie>>
    fun getUpcomingMovies(page: Int): Flow<PagingData<ResponseMovie>>
    fun searchMoviesByTerm(page: Int, query: String): Flow<PagingData<ResponseMovie>>
    fun getGenres(): Flow<List<ResponseGenre>>
    suspend fun insertFavoriteMovie(favoriteMovieId: FavoriteMovieId)
    suspend fun deleteFavoriteMovie(favoriteMovieId: FavoriteMovieId)
    fun getFavoriteMovies(): Flow<List<FavoriteMovieId>>
    suspend fun isMovieFavorite(id: Int): Boolean
    fun getMovieDetails(id: Int): Flow<Response<ResponseMovie>>
    fun searchFavoriteMoviesByTerm(term: String): Flow<List<FavoriteMovieId>>
    fun getAllFavoriteMoviesQuantity(): Flow<Int>
}
