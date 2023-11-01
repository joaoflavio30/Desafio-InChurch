package com.joaoflaviofreitas.inchurch.domain.repository

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.model.Genres
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrendingMovies(page: Int): Flow<PagingData<Movie>>
    fun getPopularMovies(page: Int): Flow<PagingData<Movie>>
    fun getUpcomingMovies(page: Int): Flow<PagingData<Movie>>
    fun searchMoviesByTerm(page: Int, query: String): Flow<PagingData<Movie>>
    fun getGenres(): Flow<Genres>
    suspend fun insertFavoriteMovie(favoriteMovieId: FavoriteMovieId)
    suspend fun deleteFavoriteMovie(favoriteMovieId: FavoriteMovieId)
    fun getFavoriteMovies(): Flow<List<FavoriteMovieId>>
    suspend fun isMovieFavorite(id: Int): Boolean
    fun getMovieDetails(id: Int): Flow<Response<Movie>>
    fun searchFavoriteMoviesByTerm(term: String): Flow<List<FavoriteMovieId>>
    fun getAllFavoriteMoviesQuantity(): Flow<Int>
}
