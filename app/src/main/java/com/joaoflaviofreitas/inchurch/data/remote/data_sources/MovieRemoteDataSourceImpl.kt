package com.joaoflaviofreitas.inchurch.data.remote.data_sources // ktlint-disable package-name

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.paging.PopularMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.paging.SearchedMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.paging.TrendingMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.paging.UpcomingMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseGenreApiDto
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseMovie
import com.joaoflaviofreitas.inchurch.data.remote.service.api.MovieApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val pagingTrendingMovies: TrendingMoviesPagingSource,
    private val pagingPopularMovies: PopularMoviesPagingSource,
    private val pagingUpcomingMovies: UpcomingMoviesPagingSource,
    private val service: MovieApi,
) : MovieRemoteDataSource {
    override fun getTrendingMovies(page: Int): Flow<PagingData<ResponseMovie>> = Pager(
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

    override fun getPopularMovies(page: Int): Flow<PagingData<ResponseMovie>> = Pager(
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

    override fun getUpcomingMovies(page: Int): Flow<PagingData<ResponseMovie>> = Pager(
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

    override fun searchMoviesByTerm(page: Int, query: String): Flow<PagingData<ResponseMovie>> {

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


    override fun getGenres(): Flow<ResponseGenreApiDto> = flow<ResponseGenreApiDto> {
        service.getGenres()
    }.catch {
        throw it
    }

    override fun getMovieDetails(id: Int): Flow<ResponseMovie> = flow<ResponseMovie> {
        service.getMovieDetails(id)
    }.catch {
        throw it
    }
}
