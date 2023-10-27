package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import kotlinx.coroutines.flow.Flow

interface SearchFavoriteMoviesByTerm {

    fun execute(term: String): Flow<List<FavoriteMovieId>>
}
