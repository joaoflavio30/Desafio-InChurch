package com.joaoflaviofreitas.inchurch.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.joaoflaviofreitas.inchurch.data.api.MovieApi
import com.joaoflaviofreitas.inchurch.data.model.ResponseMovie
import okio.IOException
import java.lang.Exception

class MoviesPagingSource(private val movieApi: MovieApi, private val pagingType: PagingType, private val query: String = "") :
    PagingSource<Int, ResponseMovie>() {

    override fun getRefreshKey(state: PagingState<Int, ResponseMovie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseMovie> {
        val pageIndex = params.key ?: 1
        return try {
            val response = when (pagingType) {
                PagingType.POPULAR_MOVIES -> {
                    movieApi.getPopularMovies(pageIndex)
                }
                PagingType.TRENDING_MOVIES -> {
                    movieApi.getTrendingMovies(pageIndex)
                }
                PagingType.UPCOMING_MOVIES -> {
                    movieApi.getUpcomingMovies(pageIndex)
                }
                PagingType.SEARCH_MOVIES -> {
                    movieApi.searchMoviesByTerm(pageIndex, query)
                }
            }
            LoadResult.Page(
                data = response.body()?.results ?: emptyList(),
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = if (pageIndex == response.body()!!.totalPages) null else pageIndex + 1,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    enum class PagingType {
        TRENDING_MOVIES, POPULAR_MOVIES, UPCOMING_MOVIES, SEARCH_MOVIES
    }
}