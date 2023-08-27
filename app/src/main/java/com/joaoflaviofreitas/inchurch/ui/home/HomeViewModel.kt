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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMovies: GetTrendingMovies,
    private val getPopularMovies: GetPopularMovies,
    private val getUpcomingMovies: GetUpcomingMovies,
    private val searchMoviesByTerm: SearchMoviesByTerm,
) : ViewModel() {

    private val _hasQuery: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val hasQuery: SharedFlow<Boolean> = _hasQuery.asSharedFlow()

    fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return getTrendingMovies.execute().cachedIn(viewModelScope)
    }
    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return getPopularMovies.execute().cachedIn(viewModelScope)
    }
    fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return getUpcomingMovies.execute().cachedIn(viewModelScope)
    }

    fun searchMoviesByTerm(query: String): Flow<PagingData<Movie>> {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isNotEmpty() && query.isNotBlank()) {
                _hasQuery.emit(true)
            } else {
                _hasQuery.emit(false)
            }
        }
        return searchMoviesByTerm.execute(query = query).cachedIn(viewModelScope)
    }
}
