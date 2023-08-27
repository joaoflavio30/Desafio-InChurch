package com.joaoflaviofreitas.inchurch.domain.usecases

import android.util.Log
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.utils.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMovieDetails(private val repository: MovieRepository) {

    fun execute(id: Int): Flow<Response<Movie>> {
        return repository.getMovieDetails(id).map { response ->
            if (response is Response.Success) {
                val movie = response.data.toMovie()
                movie.isFavorite = repository.isMovieFavorite(movie.id)
                Response.Success(movie)
            } else if (response is Response.Error) {
               Response.Error(response.errorMessage)
            } else {
             Response.Loading
            }
        }
    }
}
