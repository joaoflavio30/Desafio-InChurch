package com.joaoflaviofreitas.inchurch.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.joaoflaviofreitas.inchurch.common.constants.Constants.ERROR_MESSAGE
import com.joaoflaviofreitas.inchurch.data.local.data_source.FavoriteMovieLocalDataSource
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.data.remote.data_sources.MovieRemoteDataSource
import com.joaoflaviofreitas.inchurch.data.remote.model.GenresDto
import com.joaoflaviofreitas.inchurch.data.remote.model.MovieDto
import com.joaoflaviofreitas.inchurch.domain.model.Genres
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: FavoriteMovieLocalDataSource,
    private val mapMovieDto: (MovieDto) -> Movie,
    private val mapGenresDto: (GenresDto) -> Genres,
) : MovieRepository {
    override fun getTrendingMovies(page: Int): Flow<PagingData<Movie>> = remoteDataSource.getTrendingMovies(page).map {
        it.map { responseMovie ->
            mapMovieDto(responseMovie)
        }
    }

    override fun getPopularMovies(page: Int): Flow<PagingData<Movie>> = remoteDataSource.getPopularMovies(page).map {
        it.map { responseMovie ->
            mapMovieDto(responseMovie)
        }
    }

    override fun getUpcomingMovies(page: Int): Flow<PagingData<Movie>> = remoteDataSource.getUpcomingMovies(page).map {
        it.map { responseMovie ->
            mapMovieDto(responseMovie)
        }
    }

    override fun searchMoviesByTerm(page: Int, query: String): Flow<PagingData<Movie>> = remoteDataSource.searchMoviesByTerm(page, query).map {
        it.map { responseMovie ->
            mapMovieDto(responseMovie)
        }
    }

    override fun getGenres(): Flow<Genres> = flow {
        val result = remoteDataSource.getGenres()
        emit(mapGenresDto(result))
    }.catch { throw it }

    override suspend fun insertFavoriteMovie(favoriteMovieId: FavoriteMovieId) {
        try {
            localDataSource.addFavoriteMovie(favoriteMovieId)
        } catch (e: IOException) {
            throw IOException()
        }
    }

    override suspend fun deleteFavoriteMovie(favoriteMovieId: FavoriteMovieId) {
        try {
            localDataSource.deleteFavoriteMovie(favoriteMovieId)
        } catch (e: IOException) {
            throw IOException()
        }
    }

    override fun getFavoriteMovies(): Flow<List<FavoriteMovieId>> = localDataSource.getFavoriteMovie()

    override suspend fun isMovieFavorite(id: Int): Boolean =
        localDataSource.checkMovie(id) > 0

    override fun getMovieDetails(id: Int): Flow<Response<Movie>> = flow {
        emit(Response.Loading)
        val response = remoteDataSource.getMovieDetails(id)
        emit(Response.Success(mapMovieDto(response)))
    }.catch { Response.Error(it.message ?: ERROR_MESSAGE) }

    override fun searchFavoriteMoviesByTerm(term: String): Flow<List<FavoriteMovieId>> = localDataSource.searchFavoriteMovies(term)
    override fun getAllFavoriteMoviesQuantity(): Flow<Int> = localDataSource.getAllFavoriteMoviesQuantity()
}
