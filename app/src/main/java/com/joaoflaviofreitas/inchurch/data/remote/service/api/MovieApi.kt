package com.joaoflaviofreitas.inchurch.data.remote.service.api

import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseApiDto
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseGenreApiDto
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query(value = "page") page: Int = 1,
    ): Response<ResponseApiDto>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(value = "page") page: Int = 1,
    ): Response<ResponseApiDto>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query(value = "page") page: Int = 1,
    ): Response<ResponseApiDto>

    @GET("search/movie")
    suspend fun searchMoviesByTerm(
        @Query(value = "page") page: Int = 1,
        @Query("query") searchTerm: String = "",
        @Query("language") language: String = "en_US",
    ): Response<ResponseApiDto>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<ResponseGenreApiDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en_US",
    ): Response<ResponseMovie>
}
