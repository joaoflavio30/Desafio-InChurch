package com.joaoflaviofreitas.inchurch.domain.usecases

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetPopularMovies {

    fun execute(page: Int = 1): Flow<PagingData<Movie>>
}
