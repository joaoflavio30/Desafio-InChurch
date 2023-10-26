package com.joaoflaviofreitas.inchurch.domain.usecases

import kotlinx.coroutines.flow.Flow

interface GetAllFavoriteMoviesQuantity {

    fun execute(): Flow<Int>
}
