package com.joaoflaviofreitas.inchurch.domain

import androidx.paging.PagingData
import com.joaoflaviofreitas.inchurch.domain.repository.MovieRepository
import com.joaoflaviofreitas.inchurch.domain.usecases.GetTrendingMovies
import com.joaoflaviofreitas.inchurch.utils.TestDataClassGenerator
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class GetTrendingMoviesTest {

    private val repository: MovieRepository = mockk()
    private val getTrendingMovies: GetTrendingMovies = GetTrendingMovies(repository)
    private val testDataClassGenerator = TestDataClassGenerator()

    private val pagingDataMovies = PagingData.from(
        listOf(testDataClassGenerator.getMovie()),
    )

    private val pagingDataResponseMovie = PagingData.from(
        listOf(testDataClassGenerator.getResponseMovie()),
    )

//    @Test
//    fun `When getTrendingMovies from repository, expected Flow`() = runTest {
//        coEvery { repository.getTrendingMovies(any()) } returns flowOf(pagingDataResponseMovie)
//        val result = getTrendingMovies.execute().toList()[0].
//        assertEquals(flowOf(pagingDataMovies), result)
//    }
}
