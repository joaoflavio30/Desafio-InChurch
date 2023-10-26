package com.joaoflaviofreitas.inchurch.domain.usecases

interface IsMovieFavorite {

    suspend fun execute(id: Int): Boolean
}
