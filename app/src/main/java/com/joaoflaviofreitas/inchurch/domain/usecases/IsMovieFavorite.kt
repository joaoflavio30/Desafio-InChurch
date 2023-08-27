package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository

class IsMovieFavorite(private val repository: MovieRepository) {

    suspend fun execute(id: Int): Boolean =
        repository.isMovieFavorite(id)
}
