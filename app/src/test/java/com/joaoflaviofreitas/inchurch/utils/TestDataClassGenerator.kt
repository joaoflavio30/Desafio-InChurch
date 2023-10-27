package com.joaoflaviofreitas.inchurch.utils

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseGenre
import com.joaoflaviofreitas.inchurch.data.remote.model.ResponseMovie
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import java.util.Date

class TestDataClassGenerator {

    fun getResponseMovie(): ResponseMovie = responseMovie
    fun getMovie(): Movie = movie

    fun getGenres() = genres

    fun getResponseGenres() = responseGenres

    fun getPagingDataWithMovie(): PagingData<Movie> = pagingData

    fun getFavoriteMovieId() = favoriteMovieId

    companion object {
        val genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Science Fiction"),
        )

        val responseGenres = listOf(
            ResponseGenre(1, "Action"),
            ResponseGenre(2, "Adventure"),
            ResponseGenre(3, "Science Fiction"),
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
        val pagingData = PagingData.from(listOf(movie))

        val favoriteMovieId = FavoriteMovieId(1, "spider-man")
    }
}
