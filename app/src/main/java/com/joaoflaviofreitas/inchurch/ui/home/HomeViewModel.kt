package com.joaoflaviofreitas.inchurch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.usecases.GetPopularMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetTrendingMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetUpcomingMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchMoviesByTerm
import com.joaoflaviofreitas.inchurch.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMovies: GetTrendingMovies,
    private val getPopularMovies: GetPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val searchMoviesByTerm: SearchMoviesByTerm,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _trendingMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())
    val trendingMovies = _trendingMovies.asStateFlow()

    private val _popularMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())
    val popularMovies = _popularMovies.asStateFlow()

    private val _upcomingMovies: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())
    val upcomingMovies = _upcomingMovies.asStateFlow()

    private val _searchedMovie: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())
    val searchedMovie = _searchedMovie.asStateFlow()

    init {
        getTrendingMovies()
        getPopularMovies()
        getUpcomingMovies()
    }

    private fun getTrendingMovies() {
        viewModelScope.launch(dispatcherProvider.io) {
            getTrendingMovies.execute().cachedIn(viewModelScope)
                .collectLatest { _trendingMovies.value = it }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch(dispatcherProvider.io) {
            getPopularMovies.execute().cachedIn(viewModelScope)
                .collectLatest { _popularMovies.value = it }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch(dispatcherProvider.io) {
            getUpcomingMovies.execute().cachedIn(viewModelScope)
                .collectLatest { _upcomingMovies.value = it }
        }
    }

    fun searchMoviesByTerm(query: String): Flow<PagingData<Movie>> = searchMoviesByTerm.execute(query = query).cachedIn(viewModelScope)
}
