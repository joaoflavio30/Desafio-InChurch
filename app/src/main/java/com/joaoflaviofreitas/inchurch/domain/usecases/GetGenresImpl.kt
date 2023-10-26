package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.utils.toGenre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGenresImpl(private val repository: MovieRepository): GetGenres {
    override fun execute(): Flow<List<Genre>> = repository.getGenres().map {
        it.map { responseGenre ->
            responseGenre.toGenre()
        }
    }
}
