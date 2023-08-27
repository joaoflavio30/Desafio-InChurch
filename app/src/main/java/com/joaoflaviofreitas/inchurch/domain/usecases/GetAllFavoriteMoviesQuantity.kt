package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteMoviesQuantity(private val repository: MovieRepository) {

    fun execute(): Flow<Int> = repository.getAllFavoriteMoviesQuantity()
}
