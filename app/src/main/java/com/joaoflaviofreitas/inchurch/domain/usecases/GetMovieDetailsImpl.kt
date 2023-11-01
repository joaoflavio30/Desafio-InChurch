package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMovieDetailsImpl(private val repository: MovieRepository) : GetMovieDetails {

    override fun execute(id: Int): Flow<Response<Movie>> {
        return repository.getMovieDetails(id).map { response ->
            when (response) {
                is Response.Success -> {
                    val movie = response.data
                    movie.isFavorite = repository.isMovieFavorite(movie.id)
                    Response.Success(movie)
                }

                is Response.Error -> {
                    Response.Error(response.errorMessage)
                }

                else -> {
                    Response.Loading
                }
            }
        }
    }
}
