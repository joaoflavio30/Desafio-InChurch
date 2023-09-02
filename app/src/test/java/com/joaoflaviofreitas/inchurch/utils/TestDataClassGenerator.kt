package com.joaoflaviofreitas.inchurch.utils

import com.joaoflaviofreitas.inchurch.data.model.ResponseMovie
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import java.util.Date

class TestDataClassGenerator {

    fun getSuccessResponse(): Response<Movie> {
        val data = movie
        return Response.Success(data)
    }

    fun getErrorResponse(): Response<Movie> {
        val data = "Network Error"
        return Response.Error(data)
    }

    fun getLoadingResponse(): Response<Movie> = Response.Loading

    fun getResponseMovie(): ResponseMovie = responseMovie
    fun getMovie(): Movie = movie
    companion object {
        val genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Science Fiction"),
        )

        val movie = Movie(
            id = 123,
            posterPath = "poster_path.jpg",
            isAdult = false,
            overview = "This is the movie overview.",
            originalTitle = "Original Title",
            originalLanguage = "en",
            title = "Movie Title",
            releaseDate = Date(1679875200000), // January 26, 2023
            popularity = 7.8,
            voteCount = 1500,
            video = false,
            voteAverage = 7.5,
            backdropPath = "backdrop_path.jpg",
            genres = genres,
        )
        val responseMovie = ResponseMovie(
            id = 123,
            poster_path = "poster_path.jpg",
            adult = false,
            overview = "This is the movie overview.",
            original_title = "Original Title",
            original_language = "en",
            title = "Movie Title",
            release_date = "01-26-2023", // January 26, 2023
            popularity = 7.8,
            vote_count = 1500,
            video = false,
            vote_average = 7.5,
            backdrop_path = "backdrop_path.jpg",
            genres = genres,
        )
    }
}
