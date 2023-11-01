package com.joaoflaviofreitas.inchurch.data.local.data_source

import com.joaoflaviofreitas.inchurch.data.local.dao.MovieDao
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteMovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) : FavoriteMovieLocalDataSource {
    override suspend fun addFavoriteMovie(favoriteMovieId: FavoriteMovieId) {
        movieDao.addFavoriteMovie(favoriteMovieId)
    }

    override suspend fun deleteFavoriteMovie(favoriteMovieId: FavoriteMovieId) {
        movieDao.deleteFavoriteMovie(favoriteMovieId)
    }

    override fun getFavoriteMovie(): Flow<List<FavoriteMovieId>> =
        movieDao.getFavoriteMovie()

    override suspend fun checkMovie(id: Int): Int = movieDao.checkMovie(id)

    override fun searchFavoriteMovies(searchTerm: String): Flow<List<FavoriteMovieId>> = movieDao.searchFavoriteMovies(searchTerm)

    override fun getAllFavoriteMoviesQuantity(): Flow<Int> = movieDao.getAllFavoriteMoviesQuantity()
}
