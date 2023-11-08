package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.model.FavoriteMovieId
import kotlinx.coroutines.flow.Flow

interface SearchFavoriteMoviesByTerm {

    fun execute(term: String): Flow<List<FavoriteMovieId>>
}
