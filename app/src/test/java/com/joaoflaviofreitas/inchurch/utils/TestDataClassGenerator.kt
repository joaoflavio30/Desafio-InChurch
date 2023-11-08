package com.joaoflaviofreitas.inchurch.utils

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieIdEntity
import com.joaoflaviofreitas.inchurch.data.remote.model.GenreDto
import com.joaoflaviofreitas.inchurch.data.remote.model.MovieDto
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import java.util.Date

class TestDataClassGenerator {

    fun getResponseMovie(): MovieDto = movieDto
    fun getMovie(): Movie = movie

    fun getGenres() = genres

    fun getResponseGenres() = genreDtos

    fun getPagingDataWithMovie(): PagingData<Movie> = pagingData

    fun getFavoriteMovieId() = favoriteMovieIdEntity

    companion object {
        val genres = listOf(
            Genre(1, "Action"),
            Genre(2, "Adventure"),
            Genre(3, "Science Fiction"),
        )

        val genreDtos = listOf(
            GenreDto(1, "Action"),
            GenreDto(2, "Adventure"),
            GenreDto(3, "Science Fiction"),
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
        val movieDto = MovieDto(
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

        val favoriteMovieIdEntity = FavoriteMovieIdEntity(1, "spider-man")
    }
}
