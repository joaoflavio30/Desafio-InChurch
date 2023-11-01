package com.joaoflaviofreitas.inchurch.data.local.data_source

import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieLocalDataSource {

    suspend fun addFavoriteMovie(favoriteMovieId: FavoriteMovieId)

    suspend fun deleteFavoriteMovie(favoriteMovieId: FavoriteMovieId)

    fun getFavoriteMovie(): Flow<List<FavoriteMovieId>>

    suspend fun checkMovie(id: Int): Int

    fun searchFavoriteMovies(searchTerm: String): Flow<List<FavoriteMovieId>>

    fun getAllFavoriteMoviesQuantity(): Flow<Int>
}
