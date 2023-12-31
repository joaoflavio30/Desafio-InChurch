package com.joaoflaviofreitas.inchurch.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoflaviofreitas.inchurch.domain.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.model.Response
import com.joaoflaviofreitas.inchurch.domain.usecases.GetAllFavoriteMoviesQuantity
import com.joaoflaviofreitas.inchurch.domain.usecases.GetFavoriteMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetails
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchFavoriteMoviesByTerm
import com.joaoflaviofreitas.inchurch.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMovies: GetFavoriteMovies,
    private val getMovieDetailsImpl: GetMovieDetails,
    private val searchFavoriteMoviesByTerm: SearchFavoriteMoviesByTerm,
    private val getAllFavoriteMoviesQuantity: GetAllFavoriteMoviesQuantity,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _uiState: MutableStateFlow<Response<MutableList<Movie>>> =
        MutableStateFlow(Response.Loading)
    val uiState = _uiState.asStateFlow()

    private val _favoriteMovies: MutableStateFlow<Response<List<FavoriteMovieId>>> =
        MutableStateFlow(Response.Loading)
    val favoriteMovies = _favoriteMovies.asStateFlow()

    private val _quantityFavoriteMovies: MutableStateFlow<Int?> = MutableStateFlow(null)
    val quantityFavoriteMovie = _quantityFavoriteMovies

    init {
        getQuantityOfFavoriteMovies()
        fetchFavoritesMovies()
    }

    fun fetchFavoritesMovies() {
        viewModelScope.launch(dispatcherProvider.io) {
            getFavoriteMovies.execute().catch {
                _favoriteMovies.emit(Response.Error(it.message ?: "IO Exception"))
            }
                .collectLatest {
                    getMovies(it)
                }
        }
    }

    private fun getMovies(list: List<FavoriteMovieId>) {
        viewModelScope.launch(dispatcherProvider.io) {
            val newList = mutableListOf<Movie>()
            list.forEach {
                getMovieDetailsImpl.execute(it.id).collectLatest { response ->
                    when (response) {
                        is Response.Success -> {
                            newList.add(response.data)
                            bindDataInUiStateWhenFinishedList(newList, list)
                        }
                        is Response.Error -> {
                            _uiState.emit(Response.Error("Network Error"))
                        }
                        else -> {
                            _uiState.emit(Response.Loading)
                        }
                    }
                }
            }
            if (list.isEmpty()) {
                _uiState.emit(Response.Success(newList))
            }
        }
    }

    private suspend fun bindDataInUiStateWhenFinishedList(newList: MutableList<Movie>, list: List<FavoriteMovieId>) {
        if (newList.size == list.size) _uiState.emit(Response.Success(newList))
    }
    fun searchFavoriteMovie(term: String) {
        _uiState.value = Response.Success(mutableListOf())
        viewModelScope.launch(dispatcherProvider.io) {
            searchFavoriteMoviesByTerm.execute(term).catch {
                _favoriteMovies.emit(Response.Error(it.message ?: "IO Exception"))
            }.collectLatest {
                if (it.isEmpty()) {
                    _favoriteMovies.emit(Response.Error("Movie not found"))
                } else {
                    _favoriteMovies.emit(Response.Success(it))
                }
                getMovies(it)
            }
        }
    }

    private fun getQuantityOfFavoriteMovies() {
        viewModelScope.launch(dispatcherProvider.io) {
            getAllFavoriteMoviesQuantity.execute().catch {
                _uiState.value = Response.Error(it.message ?: "Database Error")
            }.collectLatest {
                _quantityFavoriteMovies.value = it
            }
        }
    }
}
