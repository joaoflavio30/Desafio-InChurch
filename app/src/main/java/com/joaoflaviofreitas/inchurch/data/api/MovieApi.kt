package com.joaoflaviofreitas.inchurch.data.api

import com.joaoflaviofreitas.inchurch.data.model.ResponseApi
import com.joaoflaviofreitas.inchurch.data.model.ResponseGenreApi
import com.joaoflaviofreitas.inchurch.data.model.ResponseMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "page") page: Int = 1
    ): Response<ResponseApi>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "page") page: Int = 1
    ): Response<ResponseApi>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "page") page: Int = 1
    ): Response<ResponseApi>

    @GET("search/movie")
    suspend fun searchMoviesByTerm(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "page") page: Int = 1,
        @Query("query") searchTerm: String ="",
        @Query("language") language: String = "en_US"
    ): Response<ResponseApi>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query(value = "api_key") apiKey: String
    ): Response<ResponseGenreApi>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query(value = "api_key") apiKey: String,
        @Query("language") language: String = "en_US",
    ): Response<ResponseMovie>
}