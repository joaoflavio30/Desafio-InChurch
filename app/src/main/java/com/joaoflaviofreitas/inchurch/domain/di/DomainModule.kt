package com.joaoflaviofreitas.inchurch.domain.di

import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.domain.usecases.AddFavoriteMovie
import com.joaoflaviofreitas.inchurch.domain.usecases.AddFavoriteMovieImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.DeleteFavoriteMovie
import com.joaoflaviofreitas.inchurch.domain.usecases.DeleteFavoriteMovieImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetAllFavoriteMoviesQuantity
import com.joaoflaviofreitas.inchurch.domain.usecases.GetAllFavoriteMoviesQuantityImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetFavoriteMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetFavoriteMoviesImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetGenres
import com.joaoflaviofreitas.inchurch.domain.usecases.GetGenresImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetails
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetailsImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetPopularMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetPopularMoviesImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetTrendingMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetTrendingMoviesImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.GetUpcomingMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetUpcomingMoviesImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.IsMovieFavorite
import com.joaoflaviofreitas.inchurch.domain.usecases.IsMovieFavoriteImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchFavoriteMoviesByTerm
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchFavoriteMoviesByTermImpl
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchMoviesByTerm
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchMoviesByTermImpl
import com.joaoflaviofreitas.inchurch.utils.DefaultDispatcherProvider
import com.joaoflaviofreitas.inchurch.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun providesGetTrendingMovies(repository: MovieRepository): GetTrendingMovies = GetTrendingMoviesImpl(repository)

    @Provides
    @Singleton
    fun providesGetPopularMovies(repository: MovieRepository): GetPopularMovies = GetPopularMoviesImpl(repository)

    @Provides
    @Singleton
    fun providesGetUpcomingMovies(repository: MovieRepository): GetUpcomingMovies = GetUpcomingMoviesImpl(repository)

    @Provides
    @Singleton
    fun providesSearchMoviesByTerm(repository: MovieRepository): SearchMoviesByTerm = SearchMoviesByTermImpl(repository)

    @Provides
    @Singleton
    fun providesGetGenres(repository: MovieRepository): GetGenres = GetGenresImpl(repository)

    @Provides
    @Singleton
    fun providesGetFavoriteMovies(repository: MovieRepository): GetFavoriteMovies = GetFavoriteMoviesImpl(repository)

    @Provides
    @Singleton
    fun providesAddFavoriteMovie(repository: MovieRepository): AddFavoriteMovie = AddFavoriteMovieImpl(repository)

    @Provides
    @Singleton
    fun providesDeleteFavoriteMovie(repository: MovieRepository): DeleteFavoriteMovie = DeleteFavoriteMovieImpl(repository)

    @Provides
    @Singleton
    fun providesIsMovieFavorite(repository: MovieRepository): IsMovieFavorite = IsMovieFavoriteImpl(repository)

    @Provides
    @Singleton
    fun providesGetMovieDetails(repository: MovieRepository): GetMovieDetails = GetMovieDetailsImpl(repository)

    @Provides
    @Singleton
    fun providesSearchFavoriteMoviesByTerm(repository: MovieRepository): SearchFavoriteMoviesByTerm = SearchFavoriteMoviesByTermImpl(repository)

    @Provides
    @Singleton
    fun providesGetAllFavoriteMoviesQuantity(repository: MovieRepository): GetAllFavoriteMoviesQuantity = GetAllFavoriteMoviesQuantityImpl(repository)

    @Provides
    @Singleton
    fun providesDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()
}
