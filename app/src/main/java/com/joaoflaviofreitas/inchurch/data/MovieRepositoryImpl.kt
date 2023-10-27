package com.joaoflaviofreitas.inchurch.data

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.local.MovieDao
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.data.remote.data_sources.MovieRemoteDataSource
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseGenre
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseMovie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.utils.Constants.ERROR_MESSAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieDao: MovieDao,
) : MovieRepository {
    override fun getTrendingMovies(page: Int): Flow<PagingData<ResponseMovie>> = remoteDataSource.getTrendingMovies(page)

    override fun getPopularMovies(page: Int): Flow<PagingData<ResponseMovie>> = remoteDataSource.getPopularMovies(page)

    override fun getUpcomingMovies(page: Int): Flow<PagingData<ResponseMovie>> = remoteDataSource.getUpcomingMovies(page)

    override fun searchMoviesByTerm(page: Int, query: String): Flow<PagingData<ResponseMovie>> = remoteDataSource.searchMoviesByTerm(page, query)

    override fun getGenres(): Flow<List<ResponseGenre>> = remoteDataSource.getGenres().map {
        it.genres
    }

    override suspend fun insertFavoriteMovie(favoriteMovieId: FavoriteMovieId) {
        try {
            movieDao.addFavoriteMovie(favoriteMovieId)
        } catch (e: IOException) {
            throw IOException()
        }
    }

    override suspend fun deleteFavoriteMovie(favoriteMovieId: FavoriteMovieId) {
        try {
            movieDao.deleteFavoriteMovie(favoriteMovieId)
        } catch (e: IOException) {
            throw IOException()
        }
    }

    override fun getFavoriteMovies(): Flow<List<FavoriteMovieId>> = movieDao.getFavoriteMovie()

    override suspend fun isMovieFavorite(id: Int): Boolean =
        movieDao.checkMovie(id) > 0

    override fun getMovieDetails(id: Int): Flow<Response<ResponseMovie>> = remoteDataSource.getMovieDetails(id).map {
        Response.Success(it)
    }.catch { Response.Error(it.message ?: ERROR_MESSAGE) }

    override fun searchFavoriteMoviesByTerm(term: String): Flow<List<FavoriteMovieId>> = movieDao.searchFavoriteMovies(term)
    override fun getAllFavoriteMoviesQuantity(): Flow<Int> = movieDao.getAllFavoriteMoviesQuantity()
}
