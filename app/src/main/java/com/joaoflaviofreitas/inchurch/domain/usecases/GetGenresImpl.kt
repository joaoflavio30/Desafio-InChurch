package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.model.Genres
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetGenresImpl(private val repository: MovieRepository) : GetGenres {
    override fun execute(): Flow<Genres> = repository.getGenres()
}
