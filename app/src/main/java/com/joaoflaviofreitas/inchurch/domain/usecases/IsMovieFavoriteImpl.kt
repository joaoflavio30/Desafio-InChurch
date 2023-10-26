package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository

class IsMovieFavoriteImpl(private val repository: MovieRepository) : IsMovieFavorite {

    override suspend fun execute(id: Int): Boolean =
        repository.isMovieFavorite(id)
}
