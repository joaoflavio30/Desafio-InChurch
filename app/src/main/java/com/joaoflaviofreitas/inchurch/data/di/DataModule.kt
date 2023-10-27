package com.joaoflaviofreitas.inchurch.data.di

import android.content.Context
import androidx.room.Room
import com.joaoflaviofreitas.inchurch.data.MovieRepositoryImpl
import com.joaoflaviofreitas.inchurch.data.local.FavoriteMovieDatabase
import com.joaoflaviofreitas.inchurch.data.local.MovieDao
import com.joaoflaviofreitas.inchurch.data.paging.PopularMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.paging.TrendingMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.paging.UpcomingMoviesPagingSource
import com.joaoflaviofreitas.inchurch.data.remote.data_sources.MovieRemoteDataSource
import com.joaoflaviofreitas.inchurch.data.remote.data_sources.MovieRemoteDataSourceImpl
import com.joaoflaviofreitas.inchurch.data.remote.service.api.ConnectivityInterceptor
import com.joaoflaviofreitas.inchurch.data.remote.service.api.MovieApi
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

    @Provides
    @Singleton
    fun provideMovieRepository(remoteDataSource: MovieRemoteDataSource, movieDao: MovieDao): MovieRepository = MovieRepositoryImpl(remoteDataSource, movieDao)

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
}
