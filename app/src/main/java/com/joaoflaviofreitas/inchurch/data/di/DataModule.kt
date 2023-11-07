package com.joaoflaviofreitas.inchurch.data.di

import android.content.Context
import androidx.room.Room
import com.joaoflaviofreitas.inchurch.common.extensions.mapNullInputList
import com.joaoflaviofreitas.inchurch.data.local.dao.MovieDao
import com.joaoflaviofreitas.inchurch.data.local.data_source.FavoriteMovieLocalDataSource
import com.joaoflaviofreitas.inchurch.data.local.data_source.FavoriteMovieLocalDataSourceImpl
import com.joaoflaviofreitas.inchurch.data.local.database.FavoriteMovieDatabase
import com.joaoflaviofreitas.inchurch.data.local.model.FavoriteMovieIdEntity
import com.joaoflaviofreitas.inchurch.data.mapper.mapFavoriteMovieId
import com.joaoflaviofreitas.inchurch.data.mapper.mapFavoriteMovieIdEntity
import com.joaoflaviofreitas.inchurch.data.mapper.mapGenreDto
import com.joaoflaviofreitas.inchurch.data.mapper.mapGenresDto
import com.joaoflaviofreitas.inchurch.data.mapper.mapMovieDto
import com.joaoflaviofreitas.inchurch.data.remote.paging.PopularMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.remote.paging.TrendingMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.remote.paging.UpcomingMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.remote.data_sources.MovieRemoteDataSource
import com.joaoflaviofreitas.inchurch.data.remote.data_sources.MovieRemoteDataSourceImpl
import com.joaoflaviofreitas.inchurch.data.remote.model.GenresDto
import com.joaoflaviofreitas.inchurch.data.remote.model.MovieDto
import com.joaoflaviofreitas.inchurch.data.remote.service.ConnectivityInterceptor
import com.joaoflaviofreitas.inchurch.data.remote.service.MovieApi
import com.joaoflaviofreitas.inchurch.data.repository.MovieRepositoryImpl
import com.joaoflaviofreitas.inchurch.domain.model.FavoriteMovieId
import com.joaoflaviofreitas.inchurch.domain.model.Genres
import com.joaoflaviofreitas.inchurch.domain.model.Movie
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private fun makeMovieDtoMapper(): (MovieDto) -> Movie = { movieDto ->
        mapMovieDto(movieDto)
    }

    private fun makeGenresDtoMapper(): (GenresDto) -> Genres = { genresDto ->
        mapGenresDto(genresDto) { listGenreDto ->
            mapNullInputList(listGenreDto) { genreDto ->
                mapGenreDto(genreDto)
            }
        }
    }

    private fun makeFavoriteMovieIdEntityMapper(): (FavoriteMovieIdEntity) -> FavoriteMovieId = { favoriteMovieIdEntity ->
        mapFavoriteMovieIdEntity(favoriteMovieIdEntity)
    }

    private fun makeFavoriteMovieIdMapper(): (FavoriteMovieId) -> FavoriteMovieIdEntity = { favoriteMovieId ->
        mapFavoriteMovieId(favoriteMovieId)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(remoteDataSource: MovieRemoteDataSource, localDataSource: FavoriteMovieLocalDataSource): MovieRepository = MovieRepositoryImpl(remoteDataSource, localDataSource, makeMovieDtoMapper(), makeGenresDtoMapper(), makeFavoriteMovieIdMapper(), makeFavoriteMovieIdEntityMapper())

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(@ApplicationContext context: Context): ConnectivityInterceptor {
        return ConnectivityInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(connectivityInterceptor: ConnectivityInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(connectivityInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): MovieApi =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavoriteMovieDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            FavoriteMovieDatabase::class.java,
            "favorite_movie_db",
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(database: FavoriteMovieDatabase): MovieDao = database.movieDao()

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(
        pagingTrendingMovies: TrendingMoviesPagingSource,
        pagingPopularMovies: PopularMoviesPagingSource,
        pagingUpcomingMovies: UpcomingMoviesPagingSource,
        service: MovieApi,
    ): MovieRemoteDataSource = MovieRemoteDataSourceImpl(pagingTrendingMovies, pagingPopularMovies, pagingUpcomingMovies, service)

    @Provides
    @Singleton
    fun provideFavoriteMovieLocalDataSource(
        movieDao: MovieDao,
    ): FavoriteMovieLocalDataSource = FavoriteMovieLocalDataSourceImpl(movieDao)
}
