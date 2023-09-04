package com.joaoflaviofreitas.inchurch.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.api.MovieApi
import com.joaoflaviofreitas.inchurch.data.local.MovieDao
import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.data.model.ResponseGenre
import com.joaoflaviofreitas.inchurch.data.model.ResponseMovie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.paging.MoviesPagingSource
import com.joaoflaviofreitas.inchurch.utils.Constants.API_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val movieApi: MovieApi, private val movieDao: MovieDao) : MovieRepository {
    override fun getTrendingMovies(page: Int): Flow<PagingData<ResponseMovie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 60,
            enablePlaceholders = true,
        ),
        pagingSourceFactory = {
            MoviesPagingSource(movieApi, MoviesPagingSource.PagingType.TRENDING_MOVIES)
        },
    ).flow

    override fun getPopularMovies(page: Int): Flow<PagingData<ResponseMovie>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 60,
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    movieApi,
                    MoviesPagingSource.PagingType.POPULAR_MOVIES,
                )
            },
        ).flow

    override fun getUpcomingMovies(page: Int): Flow<PagingData<ResponseMovie>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 60,
        ),
        pagingSourceFactory = {
            MoviesPagingSource(
                movieApi,
                MoviesPagingSource.PagingType.UPCOMING_MOVIES,
            )
        },
    ).flow

    override fun searchMoviesByTerm(page: Int, query: String): Flow<PagingData<ResponseMovie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 60,
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    movieApi,
                    MoviesPagingSource.PagingType.SEARCH_MOVIES,
                    query,
                )
            },
        ).flow
    }

    override fun getGenres(): Flow<List<ResponseGenre>> = flow {
        try {
            val result = movieApi.getGenres(API_KEY).body()?.genres ?: emptyList()
            emit(result)
        } catch (e: IOException) {
            throw IOException()
        } catch (e: HttpException) {
            throw e
        }
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

    override fun getMovieDetails(id: Int): Flow<Response<ResponseMovie>> = flow {
        try {
            emit(Response.Loading)
            val result = movieApi.getMovieDetails(id, API_KEY).body()
            if (result != null) {
                emit(Response.Success(result))
            }
        } catch (e: IOException) {
            Response.Error(e.message ?: "Network Error")
        }
    }

    override fun searchFavoriteMoviesByTerm(term: String): Flow<List<FavoriteMovieId>> = movieDao.searchFavoriteMovies(term)
    override fun getAllFavoriteMoviesQuantity(): Flow<Int> = movieDao.getAllFavoriteMoviesQuantity()
}
