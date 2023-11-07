package com.joaoflaviofreitas.inchurch.data.remote.data_sources // ktlint-disable package-name

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.remote.paging.PopularMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.remote.paging.SearchedMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.remote.paging.TrendingMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.remote.paging.UpcomingMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.remote.model.GenresDto
import com.joaoflaviofreitas.inchurch.data.remote.model.MovieDto
import com.joaoflaviofreitas.inchurch.data.remote.service.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val pagingTrendingMovies: TrendingMoviesPagingSource,
    private val pagingPopularMovies: PopularMoviesPagingSource,
    private val pagingUpcomingMovies: UpcomingMoviesPagingSource,
    private val service: MovieApi,
) : MovieRemoteDataSource {
    override fun getTrendingMovies(page: Int): Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 60,
            enablePlaceholders = true,
        ),
        pagingSourceFactory = {
            pagingTrendingMovies
        },
    ).flow.catch {
        throw it
    }

    override fun getPopularMovies(page: Int): Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 60,
            enablePlaceholders = true,
        ),
        pagingSourceFactory = {
            pagingPopularMovies
        },
    ).flow.catch {
        throw it
    }

    override fun getUpcomingMovies(page: Int): Flow<PagingData<MovieDto>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 60,
            enablePlaceholders = true,
        ),
        pagingSourceFactory = {
            pagingUpcomingMovies
        },
    ).flow.catch {
        throw it
    }

    override fun searchMoviesByTerm(page: Int, query: String): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 60,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                SearchedMoviesPagingSource(service, query)
            },
        ).flow.catch {
            throw it
        }
    }

    override suspend fun getGenres(): GenresDto {
        val response = service.getGenres()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun getMovieDetails(id: Int): MovieDto {
        val response = service.getMovieDetails(id)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }
}
