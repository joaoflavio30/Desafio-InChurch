package com.joaoflaviofreitas.inchurch.data.local.data_source

import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieIdEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieLocalDataSource {

    suspend fun addFavoriteMovie(favoriteMovieIdEntity: FavoriteMovieIdEntity)

    suspend fun deleteFavoriteMovie(favoriteMovieIdEntity: FavoriteMovieIdEntity)

    fun getFavoriteMovie(): Flow<List<FavoriteMovieIdEntity>>

    suspend fun checkMovie(id: Int): Int

    fun searchFavoriteMovies(searchTerm: String): Flow<List<FavoriteMovieIdEntity>>

    fun getAllFavoriteMoviesQuantity(): Flow<Int>
}
