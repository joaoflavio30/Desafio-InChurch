package com.joaoflaviofreitas.inchurch.domain.usecases

import android.util.Log
import com.joaoflaviofreitas.inchurch.data.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository

class AddFavoriteMovie(private val repository: MovieRepository) {
   suspend fun execute(favoriteMovieId: FavoriteMovieId)  {
      Log.d("add", "addfavoritemOvie chamado")
      repository.insertFavoriteMovie(favoriteMovieId)
   }
}