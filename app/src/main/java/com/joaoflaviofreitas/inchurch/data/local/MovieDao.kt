package com.joaoflaviofreitas.inchurch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun addFavoriteMovie(favoriteMovieId: FavoriteMovieId)

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovieId: FavoriteMovieId)

    @Query("SELECT * FROM favorite_movie")
    fun getFavoriteMovie(): Flow<List<FavoriteMovieId>>

    @Query("SELECT count(*) FROM favorite_movie WHERE favorite_movie.id =:id")
    suspend fun checkMovie(id: Int): Int

    @Query("SELECT * FROM favorite_movie WHERE title LIKE '%' || :searchTerm || '%' ORDER BY id DESC")
    fun searchFavoriteMovies(searchTerm: String): Flow<List<FavoriteMovieId>>

    @Query("SELECT COUNT(*) FROM favorite_movie")
    fun getAllFavoriteMoviesQuantity(): Flow<Int>
}
