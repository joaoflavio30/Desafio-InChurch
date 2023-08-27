package com.joaoflaviofreitas.inchurch.domain.di

import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.domain.usecases.AddFavoriteMovie
import com.joaoflaviofreitas.inchurch.domain.usecases.DeleteFavoriteMovie
import com.joaoflaviofreitas.inchurch.domain.usecases.GetAllFavoriteMoviesQuantity
import com.joaoflaviofreitas.inchurch.domain.usecases.GetFavoriteMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetGenres
import com.joaoflaviofreitas.inchurch.domain.usecases.GetMovieDetails
import com.joaoflaviofreitas.inchurch.domain.usecases.GetPopularMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetTrendingMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.GetUpcomingMovies
import com.joaoflaviofreitas.inchurch.domain.usecases.IsMovieFavorite
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchFavoriteMoviesByTerm
import com.joaoflaviofreitas.inchurch.domain.usecases.SearchMoviesByTerm
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
    fun providesGetTrendingMovies(repository: MovieRepository): GetTrendingMovies = GetTrendingMovies(repository)

    @Provides
    @Singleton
    fun providesGetPopularMovies(repository: MovieRepository): GetPopularMovies = GetPopularMovies(repository)

    @Provides
    @Singleton
    fun providesGetUpcomingMovies(repository: MovieRepository): GetUpcomingMovies = GetUpcomingMovies(repository)

    @Provides
    @Singleton
    fun providesSearchMoviesByTerm(repository: MovieRepository): SearchMoviesByTerm = SearchMoviesByTerm(repository)

    @Provides
    @Singleton
    fun providesGetGenres(repository: MovieRepository): GetGenres = GetGenres(repository)

    @Provides
    @Singleton
    fun providesGetFavoriteMovies(repository: MovieRepository): GetFavoriteMovies = GetFavoriteMovies(repository)

    @Provides
    @Singleton
    fun providesAddFavoriteMovie(repository: MovieRepository): AddFavoriteMovie = AddFavoriteMovie(repository)

    @Provides
    @Singleton
    fun providesDeleteFavoriteMovie(repository: MovieRepository): DeleteFavoriteMovie = DeleteFavoriteMovie(repository)

    @Provides
    @Singleton
    fun providesIsMovieFavorite(repository: MovieRepository): IsMovieFavorite = IsMovieFavorite(repository)

    @Provides
    @Singleton
    fun providesGetMovieDetails(repository: MovieRepository): GetMovieDetails = GetMovieDetails(repository)

    @Provides
    @Singleton
    fun providesSearchFavoriteMoviesByTerm(repository: MovieRepository): SearchFavoriteMoviesByTerm = SearchFavoriteMoviesByTerm(repository)

    @Provides
    @Singleton
    fun providesGetAllFavoriteMoviesQuantity(repository: MovieRepository): GetAllFavoriteMoviesQuantity = GetAllFavoriteMoviesQuantity(repository)
}
