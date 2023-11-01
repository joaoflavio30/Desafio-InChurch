package com.joaoflaviofreitas.inchurch.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.model.Genre
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.usecases.AddFavoriteMovie
import com.joaoflaviofreitas.inchurch.domain.usecases.DeleteFavoriteMovie
import com.joaoflaviofreitas.inchurch.domain.usecases.GetGenres
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetails
import com.joaoflaviofreitas.inchurch.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getGenres: GetGenres,
    private val addFavoriteMovie: AddFavoriteMovie,
    private val deleteFavoriteMovie: DeleteFavoriteMovie,
    private val getMovieDetailsImpl: GetMovieDetails,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _genres: MutableStateFlow<List<Genre>> = MutableStateFlow(emptyList())
    val genres = _genres.asStateFlow()

    private val _favoriteMovie: MutableSharedFlow<Boolean> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val favoriteMovie = _favoriteMovie.asSharedFlow()

    private val _uiState: MutableStateFlow<Response<Movie?>> = MutableStateFlow(Response.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchGenres()
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(dispatcherProvider.io) {
            getMovieDetailsImpl.execute(movieId).collectLatest {
                _uiState.value = it
            }
        }
    }

    fun addOrRemoveFavoriteMovie(favoriteMovieId: FavoriteMovieId, movie: Movie) {
        viewModelScope.launch(dispatcherProvider.io) {
            if (movie.isFavorite) {
                _favoriteMovie.emit(false)
                if (uiState.value is Response.Success) {
                    movie.isFavorite = false
                    _uiState.emit(Response.Success(movie))
                }
                deleteFavoriteMovie.execute(favoriteMovieId)
            } else {
                _favoriteMovie.emit(true)
                if (uiState.value is Response.Success) {
                    movie.isFavorite = true
                    _uiState.emit(Response.Success(movie))
                }
                addFavoriteMovie.execute(favoriteMovieId)
            }
        }
    }

    private fun fetchGenres() {
        viewModelScope.launch(dispatcherProvider.io) {
            getGenres.execute().collectLatest {
                _genres.value = it.genres
            }
        }
    }
}
