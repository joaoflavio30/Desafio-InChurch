package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoriteMoviesQuantityImpl(private val repository: MovieRepository) : GetAllFavoriteMoviesQuantity {

    override fun execute(): Flow<Int> = repository.getAllFavoriteMoviesQuantity()
}
