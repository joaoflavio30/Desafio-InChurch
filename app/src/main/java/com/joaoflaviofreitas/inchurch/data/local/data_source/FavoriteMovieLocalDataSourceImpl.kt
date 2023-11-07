package com.joaoflaviofreitas.inchurch.data.local.data_source

import com.joaoflaviofreitas.inchurch.data.local.dao.MovieDao
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieIdEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteMovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) : FavoriteMovieLocalDataSource {
    override suspend fun addFavoriteMovie(favoriteMovieIdEntity: FavoriteMovieIdEntity) {
        movieDao.addFavoriteMovie(favoriteMovieIdEntity)
    }

    override suspend fun deleteFavoriteMovie(favoriteMovieIdEntity: FavoriteMovieIdEntity) {
        movieDao.deleteFavoriteMovie(favoriteMovieIdEntity)
    }

    override fun getFavoriteMovie(): Flow<List<FavoriteMovieIdEntity>> =
        movieDao.getFavoriteMovie()

    override suspend fun checkMovie(id: Int): Int = movieDao.checkMovie(id)

    override fun searchFavoriteMovies(searchTerm: String): Flow<List<FavoriteMovieIdEntity>> = movieDao.searchFavoriteMovies(searchTerm)

    override fun getAllFavoriteMoviesQuantity(): Flow<Int> = movieDao.getAllFavoriteMoviesQuantity()
}
