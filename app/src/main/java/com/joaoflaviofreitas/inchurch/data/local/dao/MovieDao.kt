package com.joaoflaviofreitas.inchurch.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieIdEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun addFavoriteMovie(favoriteMovieIdEntity: FavoriteMovieIdEntity)

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovieIdEntity: FavoriteMovieIdEntity)

    @Query("SELECT * FROM favorite_movie")
    fun getFavoriteMovie(): Flow<List<FavoriteMovieIdEntity>>

    @Query("SELECT count(*) FROM favorite_movie WHERE favorite_movie.id =:id")
    suspend fun checkMovie(id: Int): Int

    @Query("SELECT * FROM favorite_movie WHERE title LIKE '%' || :searchTerm || '%' ORDER BY id DESC")
    fun searchFavoriteMovies(searchTerm: String): Flow<List<FavoriteMovieIdEntity>>

    @Query("SELECT COUNT(*) FROM favorite_movie")
    fun getAllFavoriteMoviesQuantity(): Flow<Int>
}
