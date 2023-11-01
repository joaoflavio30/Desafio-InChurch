package com.joaoflaviofreitas.inchurch.data.remote.service

import com.joaoflaviofreitas.inchurch.data.remote.model.PageDto
import com.joaoflaviofreitas.inchurch.data.remote.model.GenresDto
import com.joaoflaviofreitas.inchurch.data.remote.model.MovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query(value = "page") page: Int = 1,
    ): Response<PageDto>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(value = "page") page: Int = 1,
    ): Response<PageDto>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query(value = "page") page: Int = 1,
    ): Response<PageDto>

    @GET("search/movie")
    suspend fun searchMoviesByTerm(
        @Query(value = "page") page: Int = 1,
        @Query("query") searchTerm: String = "",
        @Query("language") language: String = "en_US",
    ): Response<PageDto>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenresDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en_US",
    ): Response<MovieDto>
}
