package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface GetMovieDetails {

    fun execute(id: Int): Flow<Response<Movie>>
}
