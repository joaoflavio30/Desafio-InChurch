package com.joaoflaviofreitas.inchurch.domain.usecases

import com.joaoflaviofreitas.inchurch.domain.model.Genres
import kotlinx.coroutines.flow.Flow

interface GetGenres {

    fun execute(): Flow<Genres>
}
