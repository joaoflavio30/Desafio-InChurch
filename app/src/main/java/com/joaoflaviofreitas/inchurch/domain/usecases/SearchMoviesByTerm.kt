package com.joaoflaviofreitas.inchurch.domain.usecases

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.utils.toMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchMoviesByTerm(private val repository: MovieRepository) {

    fun execute(page: Int = 1, query: String = ""): Flow<PagingData<Movie>> =
        repository.searchMoviesByTerm(page, query).map {
            it.map { responseMovie ->
                Log.d("teste", "$responseMovie")
                responseMovie.toMovie()
            }
        }
}
